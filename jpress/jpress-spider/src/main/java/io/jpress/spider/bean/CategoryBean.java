package io.jpress.spider.bean;

import java.util.List;

public class CategoryBean {
    /**
     * id : 6
     * count : 0
     * description :
     * link : https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/category/%e4%b8%ba%e4%ba%ba%e5%a4%84%e4%b8%96/
     * name : 为人处世
     * slug : %e4%b8%ba%e4%ba%ba%e5%a4%84%e4%b8%96
     * taxonomy : category
     * parent : 0
     * meta : []
     * _links : {"self":[{"href":"https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/categories/6"}],"collection":[{"href":"https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/categories"}],"about":[{"href":"https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/taxonomies/category"}],"wp:post_type":[{"href":"https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/posts?categories=6"}],"curies":[{"name":"wp","href":"https://api.w.org/{rel}","templated":true}]}
     */

    private String id;
    private String count;
    private String description;
    private String link;
    private String name;
    private String slug;
    private String taxonomy;
    private String parent;
    private LinksEntity _links;
    private java.util.List<?> meta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public LinksEntity get_links() {
        return _links;
    }

    public void set_links(LinksEntity _links) {
        this._links = _links;
    }

    public List<?> getMeta() {
        return meta;
    }

    public void setMeta(List<?> meta) {
        this.meta = meta;
    }

    // FIXME generate failure  field _$WpPost_type33
    public static class LinksEntity {
        private java.util.List<SelfEntity> self;
        private java.util.List<CollectionEntity> collection;
        private java.util.List<AboutEntity> about;
        private java.util.List<CuriesEntity> curies;

        public List<SelfEntity> getSelf() {
            return self;
        }

        public void setSelf(List<SelfEntity> self) {
            this.self = self;
        }

        public List<CollectionEntity> getCollection() {
            return collection;
        }

        public void setCollection(List<CollectionEntity> collection) {
            this.collection = collection;
        }

        public List<AboutEntity> getAbout() {
            return about;
        }

        public void setAbout(List<AboutEntity> about) {
            this.about = about;
        }

        public static class SelfEntity {
            /**
             * href : https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/categories/6
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public static class CollectionEntity {
            /**
             * href : https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/categories
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public static class AboutEntity {
            /**
             * href : https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/taxonomies/category
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

//        public static class Wp
        /**
         * href : https://f907e830-129a-4a69-b355-e54be2e65284.coding.io/wp-json/wp/v2/posts?categories=6
         */

        private String href;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public static class CuriesEntity {
            /**
             * name : wp
             * href : https://api.w.org/{rel}
             * templated : true
             */

            private String name;
            private String href;
            private boolean templated;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public boolean isTemplated() {
                return templated;
            }

            public void setTemplated(boolean templated) {
                this.templated = templated;
            }
        }
    }
}
