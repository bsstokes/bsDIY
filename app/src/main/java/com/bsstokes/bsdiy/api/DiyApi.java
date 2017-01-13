package com.bsstokes.bsdiy.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface DiyApi {
    String ENDPOINT = "https://api.diy.org/";

    @GET("/skills?limit=1000&offset=0")
    Observable<Response<DiyResponse<List<Skill>>>> getSkills();

    @GET("/skills/{skill_url}/challenges")
    Observable<Response<DiyResponse<List<Challenge>>>> getChallenges(@Path("skill_url") String skillUrl);

    class Helper {
        @NonNull
        public static String normalizeUrl(@NonNull String url) {
            final Uri uri = Uri.parse(url);
            if (TextUtils.isEmpty(uri.getScheme())) {
                return uri.buildUpon().scheme("https").build().toString();
            } else {
                return uri.toString();
            }
        }
    }

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

    class Skill {
        public long id;
        // "sku":314889437,
        // "stamp":"2014-05-06T22:53:11.000Z",
        public boolean active = true;
        // "activeAt":null,
        public @Nullable String url = "";
        public @Nullable String title = "";
        public @Nullable String description = "";
        // "position": [0, 0],
        public @Nullable Icons icons = new Icons();
        public @Nullable Images images = new Images("", "", "");
        // "grammar": {
        //   "singular":"actor",
        //   "plural":"Actor",
        //   "subject":"Actor",
        //   "article":"a"
        // },
        // "pole": "art",
        public @Nullable String color;
        // "notes": ""

        public static class Icons {
            public @Nullable String small;
            public @Nullable String medium;
        }

        public static class Images {
            public Images(@Nullable String small, @Nullable String medium, @Nullable String large) {
                this.small = small;
                this.medium = medium;
                this.large = large;
            }

            public @Nullable String small = "";
            public @Nullable String medium = "";
            public @Nullable String large = "";
        }

        @Override
        public String toString() {
            return "Skill{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    class Challenge {
        public long id;
        public boolean active = true;
        public @Nullable String title;
        public @Nullable String description;
        public @Nullable Image image = new Image();

        public static class Image {
            public @Nullable Asset ios_600;
        }
    }

    class Asset {
        public @Nullable String url;
        public @Nullable String mime;
        public int width;
        public int height;
    }
}