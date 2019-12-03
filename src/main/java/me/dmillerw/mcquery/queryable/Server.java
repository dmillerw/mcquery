package me.dmillerw.mcquery.queryable;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import fi.iki.elonen.NanoHTTPD;
import me.dmillerw.mcquery.lib.JsonUtils;
import me.dmillerw.mcquery.lib.PosUtils;
import me.dmillerw.mcquery.queryable.data.McBlockState;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class Server extends NanoHTTPD {

    private static Map<String, Function<Context, CompletableFuture<Response>>> flatPaths = Maps.newHashMap();
    private static Map<String[], Function<Context, CompletableFuture<Response>>> paramPaths = Maps.newHashMap();

    private static void register(String path, Function<Context, Response> function) {
        Function<Context, CompletableFuture<Response>> wrapper = (ctx) -> {
            CompletableFuture<Response> future = new CompletableFuture<>();
            ThreadTaskExecutor<?> executor = LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
            executor.execute(() -> {
                future.complete(function.apply(ctx));
            });
            return future;
        };

        if (hasParameters(path)) {
            if (path.startsWith("/")) path = path.substring(1);
            paramPaths.put(path.split("/"), wrapper);
        } else {
            flatPaths.put(path, wrapper);
        }
    }

    static {
        register("/", (ctx) -> newFixedLengthResponse("{}"));
        register("/content", (ctx) -> newJsonResponse(Content.getContent()));
        register("/players", (ctx) -> newJsonResponse(Players.getAllPlayers()));
        register("/players/:uuid", (ctx) -> newJsonResponse(Players.getPlayer(UUID.fromString(ctx.getPathParam("uuid")))));
        register("/players/:uuid/inventories", (ctx) -> newJsonResponse(Players.getPlayerInventory(UUID.fromString(ctx.getPathParam("uuid")))));
        register("/worlds", (ctx) -> newJsonResponse(Worlds.getAllWorlds()));
        register("/worlds/:world", (ctx) -> newJsonResponse(Worlds.getWorld(ctx.getPathParam("world"))));
        register("/worlds/:world/entities", (ctx) -> newJsonResponse(Entities.getAllEntitiesInWorld(Worlds.getMcWorld(ctx.getPathParam("world")))));
        register("/worlds/:world/blocks/:xyz", (ctx) -> {
            World world = Worlds.getMcWorld(ctx.getPathParam("world"));
            BlockPos pos = PosUtils.fromString(ctx.getPathParam("xyz"));
            return newJsonResponse(new McBlockState(world, pos, world.getBlockState(pos)));
        });
    }

    private static Response path(IHTTPSession session) {
        String path = session.getUri();
        Context context = new Context(session);

        Function<Context, CompletableFuture<Response>> rawPath = flatPaths.get(path);
        if (rawPath != null) {
            System.out.println("raw path match!");

            try {
                return rawPath.apply(context).get();
            } catch (Exception ex) {
                return newFixedLengthResponse(ex.getLocalizedMessage() + "<br>" + Joiner.on("<br>").join(ex.getStackTrace()));
            }
        }

        if (path.startsWith("/")) path = path.substring(1);

        if (paramPaths != null || !paramPaths.isEmpty()) {
            String[] split = path.split("/");

            System.out.println("Testing path: " + Arrays.toString(split));

            for (String[] pathSegments : paramPaths.keySet()) {
                System.out.println("Testing against " + Arrays.toString(pathSegments) + " ... ");

                if (split.length != pathSegments.length) {
                    System.out.println(" ... skipping, bad length");
                    continue;
                }

                Map<String, String> params = Maps.newHashMap();
                boolean failedMatch = false;

                for (int i = 0; i < pathSegments.length; i++) {
                    if (i >= split.length) {
                        failedMatch = true;
                        break;
                    }

                    String segment = pathSegments[i];

                    if (!segment.equals(split[i])) {
                        if (!hasParameters(segment)) {
                            failedMatch = true;
                            break;
                        } else {
                            params.put(segment.replace(":", ""), split[i]);
                        }
                    }
                }

                System.out.println(failedMatch ? "failed" : "match!");

                if (!failedMatch) {
                    context.setPathParams(params);

                    try {
                        return paramPaths.get(pathSegments).apply(context).get();
                    } catch (Exception ex) {
                        return newFixedLengthResponse(ex.getLocalizedMessage() + "<br>" + Joiner.on("<br>").join(ex.getStackTrace()));
                    }
                }
            }
        }

        return newFixedLengthResponse("404");
    }

    private static boolean hasParameters(String path) {
        return path.contains(":");
    }

    public static Response newJsonResponse(Object obj) {
        return newFixedLengthResponse(JsonUtils.toJson(obj));
    }

    public static void create() {
        Server server = new Server();
        try {
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server() {
        super(8080);
    }

    @Override
    public Response serve(IHTTPSession session) {
        return path(session);
    }

    public final void startServer() throws IOException {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }
}
