package cool.lucasleabres.ruby.http;

import java.util.List;

/**
 * Created by Lucas Leabres on 9/12/2016.
 */
public class JsonModel {
    /**
     * status : 200
     * msg : OK
     */

    private MetaBean meta;
    private ResponseBean response;

    public JsonModel() {
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class MetaBean {
        private int status;
        private String msg;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class ResponseBean {
        /**
         * blog_name : iwillmindfuckyou
         * id : 150424021068
         * post_url : http://iwillmindfuckyou.tumblr.com/post/150424021068/crimewave420-memeufacturing-now-that
         * slug : crimewave420-memeufacturing-now-that
         * type : text
         * date : 2016-09-15 00:54:58 GMT
         * timestamp : 1473900898
         * state : published
         * format : html
         * reblog_key : WbsOhD1l
         * tags : []
         * short_url : https://tmblr.co/ZVRJBy2C5_6XC
         * summary : now that halloween is coming up i just want to remind you all that “skeleton war” and “spoopy” jokes are extremely fucking...
         * recommended_source : null
         * recommended_color : null
         * followed : true
         * highlighted : []
         * liked : false
         * note_count : 10062
         * source_url : http://memeufacturing.tumblr.com/post/150271885794/now-that-halloween-is-coming-up-i-just-want-to
         * source_title : memeufacturing
         * title : null
         * body : <p><a class="tumblr_blog" href="http://crimewave420.tumblr.com/post/150326642714" target="_blank">crimewave420</a>:</p>
         <blockquote>
         <p><a class="tumblr_blog" href="http://memeufacturing.tumblr.com/post/150271885794" target="_blank">memeufacturing</a>:</p>
         <blockquote>
         <p>now that halloween is coming up i just want to remind you all that “skeleton war” and “spoopy” jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired</p>
         </blockquote>
         <p>The replies to this post shaved years off my life</p>
         </blockquote>
         * reblog : {"tree_html":"<p><a class=\"tumblr_blog\" href=\"http://crimewave420.tumblr.com/post/150326642714\" target=\"_blank\">crimewave420<\/a>:<\/p><blockquote>\n<p><a class=\"tumblr_blog\" href=\"http://memeufacturing.tumblr.com/post/150271885794\" target=\"_blank\">memeufacturing<\/a>:<\/p>\n<blockquote>\n<p>now that halloween is coming up i just want to remind you all that \u201cskeleton war\u201d and \u201cspoopy\u201d jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired<\/p>\n<\/blockquote>\n<p>The replies to this post shaved years off my life<\/p>\n<\/blockquote>","comment":""}
         * trail : [{"blog":{"name":"memeufacturing","active":true,"theme":{"header_full_width":500,"header_full_height":281,"header_focus_width":499,"header_focus_height":281,"avatar_shape":"square","background_color":"#000000","body_font":"Helvetica Neue","header_bounds":"0,499,281,0","header_image":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc.gif","header_image_focused":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/7xSo8sqvb/tumblr_static_tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_focused_v3.gif","header_image_scaled":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_2048_v2.gif","header_stretch":true,"link_color":"#ffffff","show_avatar":false,"show_description":true,"show_header_image":true,"show_title":true,"title_color":"#ffffff","title_font":"Helvetica Neue","title_font_weight":"bold"},"share_likes":false,"share_following":false},"post":{"id":"150271885794"},"content_raw":"<p>now that halloween is coming up i just want to remind you all that \u201cskeleton war\u201d and \u201cspoopy\u201d jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired<\/p>","content":"<p>now that halloween is coming up i just want to remind you all that \u201cskeleton war\u201d and \u201cspoopy\u201d jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired<\/p>","is_root_item":true},{"blog":{"name":"crimewave420","active":true,"theme":{"header_full_width":332,"header_full_height":381,"header_focus_width":264,"header_focus_height":148,"avatar_shape":"square","background_color":"#2E6A67","body_font":"Helvetica Neue","header_bounds":"99,298,247,34","header_image":"https://secure.static.tumblr.com/a0dd4e4f03d8607fec8d318e16e4c010/0mddmlr/VOCnbk950/tumblr_static_1vmmzp8pbdk0cc00oocc4wcww.png","header_image_focused":"https://secure.static.tumblr.com/a0dd4e4f03d8607fec8d318e16e4c010/0mddmlr/letnbk951/tumblr_static_tumblr_static_1vmmzp8pbdk0cc00oocc4wcww_focused_v3.png","header_image_scaled":"https://secure.static.tumblr.com/a0dd4e4f03d8607fec8d318e16e4c010/0mddmlr/VOCnbk950/tumblr_static_1vmmzp8pbdk0cc00oocc4wcww_2048_v2.png","header_stretch":true,"link_color":"#000000","show_avatar":true,"show_description":true,"show_header_image":true,"show_title":true,"title_color":"#444444","title_font":"Gibson","title_font_weight":"bold"},"share_likes":false,"share_following":false},"post":{"id":"150326642714"},"content_raw":"<p>The replies to this post shaved years off my life<\/p>","content":"<p>The replies to this post shaved years off my life<\/p>"}]
         * can_send_in_message : true
         * can_reply : true
         * can_like : true
         * can_reblog : true
         * display_avatar : true
         */

        private List<PostsBean> posts;

        public List<PostsBean> getPosts() {
            return posts;
        }

        public void setPosts(List<PostsBean> posts) {
            this.posts = posts;
        }

        public static class PostsBean {
            private String blog_name;
            private long id;
            private String post_url;
            private String slug;
            private String type;
            private String date;
            private int timestamp;
            private String state;
            private String format;
            private String reblog_key;
            private String short_url;
            private String summary;
            private Object recommended_source;
            private Object recommended_color;
            private boolean followed;
            private boolean liked;
            private int note_count;
            private String source_url;
            private String source_title;
            private Object title;
            private String body;
            /**
             * tree_html : <p><a class="tumblr_blog" href="http://crimewave420.tumblr.com/post/150326642714" target="_blank">crimewave420</a>:</p><blockquote>
             <p><a class="tumblr_blog" href="http://memeufacturing.tumblr.com/post/150271885794" target="_blank">memeufacturing</a>:</p>
             <blockquote>
             <p>now that halloween is coming up i just want to remind you all that “skeleton war” and “spoopy” jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired</p>
             </blockquote>
             <p>The replies to this post shaved years off my life</p>
             </blockquote>
             * comment :
             */

            private ReblogBean reblog;
            private boolean can_send_in_message;
            private boolean can_reply;
            private boolean can_like;
            private boolean can_reblog;
            private boolean display_avatar;
            private List<?> tags;
            private List<?> highlighted;
            /**
             * blog : {"name":"memeufacturing","active":true,"theme":{"header_full_width":500,"header_full_height":281,"header_focus_width":499,"header_focus_height":281,"avatar_shape":"square","background_color":"#000000","body_font":"Helvetica Neue","header_bounds":"0,499,281,0","header_image":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc.gif","header_image_focused":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/7xSo8sqvb/tumblr_static_tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_focused_v3.gif","header_image_scaled":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_2048_v2.gif","header_stretch":true,"link_color":"#ffffff","show_avatar":false,"show_description":true,"show_header_image":true,"show_title":true,"title_color":"#ffffff","title_font":"Helvetica Neue","title_font_weight":"bold"},"share_likes":false,"share_following":false}
             * post : {"id":"150271885794"}
             * content_raw : <p>now that halloween is coming up i just want to remind you all that “skeleton war” and “spoopy” jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired</p>
             * content : <p>now that halloween is coming up i just want to remind you all that “skeleton war” and “spoopy” jokes are extremely fucking problematic. they arent actually problematic but i have been on here for three years and i am so very tired</p>
             * is_root_item : true
             */

            private List<TrailBean> trail;

            public String getBlog_name() {
                return blog_name;
            }

            public void setBlog_name(String blog_name) {
                this.blog_name = blog_name;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getPost_url() {
                return post_url;
            }

            public void setPost_url(String post_url) {
                this.post_url = post_url;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public String getReblog_key() {
                return reblog_key;
            }

            public void setReblog_key(String reblog_key) {
                this.reblog_key = reblog_key;
            }

            public String getShort_url() {
                return short_url;
            }

            public void setShort_url(String short_url) {
                this.short_url = short_url;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public Object getRecommended_source() {
                return recommended_source;
            }

            public void setRecommended_source(Object recommended_source) {
                this.recommended_source = recommended_source;
            }

            public Object getRecommended_color() {
                return recommended_color;
            }

            public void setRecommended_color(Object recommended_color) {
                this.recommended_color = recommended_color;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }

            public boolean isLiked() {
                return liked;
            }

            public void setLiked(boolean liked) {
                this.liked = liked;
            }

            public int getNote_count() {
                return note_count;
            }

            public void setNote_count(int note_count) {
                this.note_count = note_count;
            }

            public String getSource_url() {
                return source_url;
            }

            public void setSource_url(String source_url) {
                this.source_url = source_url;
            }

            public String getSource_title() {
                return source_title;
            }

            public void setSource_title(String source_title) {
                this.source_title = source_title;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public ReblogBean getReblog() {
                return reblog;
            }

            public void setReblog(ReblogBean reblog) {
                this.reblog = reblog;
            }

            public boolean isCan_send_in_message() {
                return can_send_in_message;
            }

            public void setCan_send_in_message(boolean can_send_in_message) {
                this.can_send_in_message = can_send_in_message;
            }

            public boolean isCan_reply() {
                return can_reply;
            }

            public void setCan_reply(boolean can_reply) {
                this.can_reply = can_reply;
            }

            public boolean isCan_like() {
                return can_like;
            }

            public void setCan_like(boolean can_like) {
                this.can_like = can_like;
            }

            public boolean isCan_reblog() {
                return can_reblog;
            }

            public void setCan_reblog(boolean can_reblog) {
                this.can_reblog = can_reblog;
            }

            public boolean isDisplay_avatar() {
                return display_avatar;
            }

            public void setDisplay_avatar(boolean display_avatar) {
                this.display_avatar = display_avatar;
            }

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }

            public List<?> getHighlighted() {
                return highlighted;
            }

            public void setHighlighted(List<?> highlighted) {
                this.highlighted = highlighted;
            }

            public List<TrailBean> getTrail() {
                return trail;
            }

            public void setTrail(List<TrailBean> trail) {
                this.trail = trail;
            }

            public static class ReblogBean {
                private String tree_html;
                private String comment;

                public String getTree_html() {
                    return tree_html;
                }

                public void setTree_html(String tree_html) {
                    this.tree_html = tree_html;
                }

                public String getComment() {
                    return comment;
                }

                public void setComment(String comment) {
                    this.comment = comment;
                }
            }

            public static class TrailBean {
                /**
                 * name : memeufacturing
                 * active : true
                 * theme : {"header_full_width":500,"header_full_height":281,"header_focus_width":499,"header_focus_height":281,"avatar_shape":"square","background_color":"#000000","body_font":"Helvetica Neue","header_bounds":"0,499,281,0","header_image":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc.gif","header_image_focused":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/7xSo8sqvb/tumblr_static_tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_focused_v3.gif","header_image_scaled":"https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_2048_v2.gif","header_stretch":true,"link_color":"#ffffff","show_avatar":false,"show_description":true,"show_header_image":true,"show_title":true,"title_color":"#ffffff","title_font":"Helvetica Neue","title_font_weight":"bold"}
                 * share_likes : false
                 * share_following : false
                 */

                private BlogBean blog;
                /**
                 * id : 150271885794
                 */

                private PostBean post;
                private String content_raw;
                private String content;
                private boolean is_root_item;

                public BlogBean getBlog() {
                    return blog;
                }

                public void setBlog(BlogBean blog) {
                    this.blog = blog;
                }

                public PostBean getPost() {
                    return post;
                }

                public void setPost(PostBean post) {
                    this.post = post;
                }

                public String getContent_raw() {
                    return content_raw;
                }

                public void setContent_raw(String content_raw) {
                    this.content_raw = content_raw;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public boolean isIs_root_item() {
                    return is_root_item;
                }

                public void setIs_root_item(boolean is_root_item) {
                    this.is_root_item = is_root_item;
                }

                public static class BlogBean {
                    private String name;
                    private boolean active;
                    /**
                     * header_full_width : 500
                     * header_full_height : 281
                     * header_focus_width : 499
                     * header_focus_height : 281
                     * avatar_shape : square
                     * background_color : #000000
                     * body_font : Helvetica Neue
                     * header_bounds : 0,499,281,0
                     * header_image : https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc.gif
                     * header_image_focused : https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/7xSo8sqvb/tumblr_static_tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_focused_v3.gif
                     * header_image_scaled : https://secure.static.tumblr.com/3f5ff4fe94755a294d20a4f7fc8fdd0a/jsyfx3z/Pllo8sqv7/tumblr_static_vbwdo8eoy4g4ssc4s0ck4soc_2048_v2.gif
                     * header_stretch : true
                     * link_color : #ffffff
                     * show_avatar : false
                     * show_description : true
                     * show_header_image : true
                     * show_title : true
                     * title_color : #ffffff
                     * title_font : Helvetica Neue
                     * title_font_weight : bold
                     */

                    private ThemeBean theme;
                    private boolean share_likes;
                    private boolean share_following;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public boolean isActive() {
                        return active;
                    }

                    public void setActive(boolean active) {
                        this.active = active;
                    }

                    public ThemeBean getTheme() {
                        return theme;
                    }

                    public void setTheme(ThemeBean theme) {
                        this.theme = theme;
                    }

                    public boolean isShare_likes() {
                        return share_likes;
                    }

                    public void setShare_likes(boolean share_likes) {
                        this.share_likes = share_likes;
                    }

                    public boolean isShare_following() {
                        return share_following;
                    }

                    public void setShare_following(boolean share_following) {
                        this.share_following = share_following;
                    }

                    public static class ThemeBean {
                        private int header_full_width;
                        private int header_full_height;
                        private int header_focus_width;
                        private int header_focus_height;
                        private String avatar_shape;
                        private String background_color;
                        private String body_font;
                        private String header_bounds;
                        private String header_image;
                        private String header_image_focused;
                        private String header_image_scaled;
                        private boolean header_stretch;
                        private String link_color;
                        private boolean show_avatar;
                        private boolean show_description;
                        private boolean show_header_image;
                        private boolean show_title;
                        private String title_color;
                        private String title_font;
                        private String title_font_weight;

                        public int getHeader_full_width() {
                            return header_full_width;
                        }

                        public void setHeader_full_width(int header_full_width) {
                            this.header_full_width = header_full_width;
                        }

                        public int getHeader_full_height() {
                            return header_full_height;
                        }

                        public void setHeader_full_height(int header_full_height) {
                            this.header_full_height = header_full_height;
                        }

                        public int getHeader_focus_width() {
                            return header_focus_width;
                        }

                        public void setHeader_focus_width(int header_focus_width) {
                            this.header_focus_width = header_focus_width;
                        }

                        public int getHeader_focus_height() {
                            return header_focus_height;
                        }

                        public void setHeader_focus_height(int header_focus_height) {
                            this.header_focus_height = header_focus_height;
                        }

                        public String getAvatar_shape() {
                            return avatar_shape;
                        }

                        public void setAvatar_shape(String avatar_shape) {
                            this.avatar_shape = avatar_shape;
                        }

                        public String getBackground_color() {
                            return background_color;
                        }

                        public void setBackground_color(String background_color) {
                            this.background_color = background_color;
                        }

                        public String getBody_font() {
                            return body_font;
                        }

                        public void setBody_font(String body_font) {
                            this.body_font = body_font;
                        }

                        public String getHeader_bounds() {
                            return header_bounds;
                        }

                        public void setHeader_bounds(String header_bounds) {
                            this.header_bounds = header_bounds;
                        }

                        public String getHeader_image() {
                            return header_image;
                        }

                        public void setHeader_image(String header_image) {
                            this.header_image = header_image;
                        }

                        public String getHeader_image_focused() {
                            return header_image_focused;
                        }

                        public void setHeader_image_focused(String header_image_focused) {
                            this.header_image_focused = header_image_focused;
                        }

                        public String getHeader_image_scaled() {
                            return header_image_scaled;
                        }

                        public void setHeader_image_scaled(String header_image_scaled) {
                            this.header_image_scaled = header_image_scaled;
                        }

                        public boolean isHeader_stretch() {
                            return header_stretch;
                        }

                        public void setHeader_stretch(boolean header_stretch) {
                            this.header_stretch = header_stretch;
                        }

                        public String getLink_color() {
                            return link_color;
                        }

                        public void setLink_color(String link_color) {
                            this.link_color = link_color;
                        }

                        public boolean isShow_avatar() {
                            return show_avatar;
                        }

                        public void setShow_avatar(boolean show_avatar) {
                            this.show_avatar = show_avatar;
                        }

                        public boolean isShow_description() {
                            return show_description;
                        }

                        public void setShow_description(boolean show_description) {
                            this.show_description = show_description;
                        }

                        public boolean isShow_header_image() {
                            return show_header_image;
                        }

                        public void setShow_header_image(boolean show_header_image) {
                            this.show_header_image = show_header_image;
                        }

                        public boolean isShow_title() {
                            return show_title;
                        }

                        public void setShow_title(boolean show_title) {
                            this.show_title = show_title;
                        }

                        public String getTitle_color() {
                            return title_color;
                        }

                        public void setTitle_color(String title_color) {
                            this.title_color = title_color;
                        }

                        public String getTitle_font() {
                            return title_font;
                        }

                        public void setTitle_font(String title_font) {
                            this.title_font = title_font;
                        }

                        public String getTitle_font_weight() {
                            return title_font_weight;
                        }

                        public void setTitle_font_weight(String title_font_weight) {
                            this.title_font_weight = title_font_weight;
                        }
                    }
                }

                public static class PostBean {
                    private String id;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }
                }
            }
        }
    }
}
