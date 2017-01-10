package com.bsstokes.bsdiy.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface DiyApi {
    String ENDPOINT = "https://api.diy.org/";

    @GET("/")
    Observable<Response<InfoResponse>> getApiInfo();

    class InfoResponse {
        Head head = new Head();

        static class Head {
            int code;
            String status = "";

            Collection collection = new Collection();

            static class Collection {
                int limit;
                int offset;

                @Override
                public String toString() {
                    return "Collection{" +
                            "limit=" + limit +
                            ", offset=" + offset +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "Head{" +
                        "code=" + code +
                        ", status='" + status + '\'' +
                        ", collection=" + collection +
                        '}';
            }
        }

        Info response = new Info();

        static class Info {
            String docs = "";
            String help = "";
            String twitter = "";

            @Override
            public String toString() {
                return "Info{" +
                        "docs='" + docs + '\'' +
                        ", help='" + help + '\'' +
                        ", twitter='" + twitter + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "InfoResponse{" +
                    "head=" + head +
                    ", response=" + response +
                    '}';
        }
    }
}
