package com.bsstokes.bsdiy.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface DiyApi {
    String ENDPOINT = "https://api.diy.org/";

    @GET("/")
    Observable<Response<DiyResponse<DiyInfo>>> getApiInfo();

    class DiyResponse<T> {
        Head head = new Head();
        T response;

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

        @Override
        public String toString() {
            return "DiyResponse{" +
                    "head=" + head +
                    ", response=" + response +
                    '}';
        }
    }

    class DiyInfo {
        String docs = "";
        String help = "";
        String twitter = "";

        @Override
        public String toString() {
            return "DiyInfo{" +
                    "docs='" + docs + '\'' +
                    ", help='" + help + '\'' +
                    ", twitter='" + twitter + '\'' +
                    '}';
        }
    }
}
