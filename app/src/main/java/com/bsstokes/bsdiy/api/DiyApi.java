package com.bsstokes.bsdiy.api;

import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

public interface DiyApi {
    String ENDPOINT = "https://api.diy.org/";

    @GET("/")
    Observable<Response<DiyResponse<DiyInfo>>> getApiInfo();

    @GET("/skills?limit=1000&offset=0")
    Observable<Response<DiyResponse<List<Skill>>>> getSkills();

    class DiyResponse<T> {
        Head head = new Head();
        public T response;

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

    class Skill {
        public long id;
        // "sku":314889437,
        // "stamp":"2014-05-06T22:53:11.000Z",
        public boolean active;
        // "activeAt":null,
        public @Nullable String url = "";
        public @Nullable String title = "";
        public @Nullable String description = "";
        // "position": [0, 0],
        // "icons": {
        //   "small":"//d1973c4qjhao9m.cloudfront.net/patches/actor_icon_small.png",
        //   "medium":"//d1973c4qjhao9m.cloudfront.net/patches/actor_icon_medium.png"
        // },
        // "images": {
        //   "small":"//d1973c4qjhao9m.cloudfront.net/patches/actor_icon_small.png",
        //   "medium":"//d1973c4qjhao9m.cloudfront.net/patches/actor_medium.png",
        //   "large":"//d1973c4qjhao9m.cloudfront.net/patches/actor_large.png"
        // },
        // "grammar": {
        //   "singular":"actor",
        //   "plural":"Actor",
        //   "subject":"Actor",
        //   "article":"a"
        // },
        // "pole": "art",
        public @Nullable String color;
        // "notes": ""

        @Override
        public String toString() {
            return "Skill{" +
                    "id=" + id +
                    ", active=" + active +
                    ", url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
