(function(window) {
    var path = window.location.pathname;
    window._location = {
        hash: "hehe",
        host: "haha",
        hostname: "hehehe",
        href: "ololo",
        origin: "lolo",
        pathname: "hehe",
        port: "80",
        protocol: "http:",
        assign: function(url) {
            window.location.assign($url(url));
        },
        reload: function(bool) {
            window.location.assign(bool);
        },
        replace: function(url) {
            window.location.replace($url(url));
        },
        toString: function() {
            return "http://google.com/";
        },
        search: function(str) {
            window.location.search(str);
        },
        toString: function() {
            return "he he he";
        }
    };

    function base_host(path) {
        parts = path.split("/");
        if (parts[1] == "s") {
            return "/s/" + parts[2];
        } else {
            return "/" + parts[1];
        }
    }

    function base_url(path) {
        if (path[path.length - 1] == '/') {
            return path;
        } else {
            parts = path.split("/");
            if (parts > 2) {
                parts.remove(parts.length - 1);
                return "/" + parts.join('/') + "/";
            } else {
                return path + "/";
            }
        }
    }

    var baseHost = base_host(path);
    var baseUrl = base_url(path);

    function safe_url(url) {

        if (url.indexOf("http://") === 0) {
            return url.replace("http://", "/");
        } else if (url.indexOf("//") === 0) {
            return url.replace("//", "/");
        } else if (url.indexOf("https://") === 0) {
            return url.replace("https://", "/s/");
        } else if (url.indexOf("/") === 0) {
            return baseHost + url;
        } else {
            return baseUrl + url;
        }

    }

    function is_safe_url(url) {
        return url.indexOf("/s/") === 0 || url.indexOf(baseHost) === 0 || url.indexOf("data:image") === 0 || url.indexOf("#") === 0 || url.match(/^\/[\w\.\:\@\-]{3,}\.[\w]{2,5}(:[0-9]+)?/g);
    }

    function $url(url) {
        if (!is_safe_url(url)) {
            return safe_url(url);
        } else {
            return url;
        }
    }

    function check_attribute(node, attrName) {
        if (node instanceof Element && node.hasAttribute(attrName)) {
            url = node.getAttribute(attrName);
            if (!is_safe_url(url)) {
                var safeUrl = safe_url(url);
                console.log("protecting url " + url + " safe_url = " + safeUrl);
                node.setAttribute(attrName, safeUrl);
            } else {
                console.log("url is safe " + url);
            }
        }
    }

    function protect_node(node) {
        function attrs(node) {
            check_attribute(node, "href");
            check_attribute(node, "src");
            check_attribute(node, "action");
        }
        attrs(node);
        for (var i = 0; i < node.childNodes.length; i++) {
            item = node[i];
            attrs(node);
        }
    }

    (function($open) {
        XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
            if (is_safe_url(url)) {
                var sUrl = url;
            } else {
                var sUrl = safe_url(url);
                console.log(url + " redirected to " + sUrl);
            }
            return $open.call(this, method, sUrl, async, user, pass);
        };
    })(XMLHttpRequest.prototype.open);

    (function($appendChild, $insertBefore, $replaceChild, $setAttribute, $setAttributeNS, $setAttributeNode, $setAttributeNodeNS) {
        Node.prototype.appendChild = function(node) {
            protect_node(node);
            return $appendChild.call(this, node);
        };
        Node.prototype.insertBefore = function(newElement, referenceElement) {
            protect_node(newElement);
            return $insertBefore.call(this, newElement, referenceElement);
        };
        Node.prototype.replaceChild = function(newChild, oldChild) {
            protect_node(newChild);
            return $replaceChild.call(this, newChild, oldChild);
        };
        Element.prototype.setAttribute = function(name, value) {
            if (name == "href" || name == "src" || name == "action") {
                value = $url(value);
            }

            return $setAttribute.call(this, name, value);
        };
        Element.prototype.setAttributeNS = function(namespace, name, value) {
            if (name == "href" || name == "src" || name == "action") {
                value = $url(value);
            }

            return $setAttributeNS.call(this, namespace, name, value);
        };
        Element.prototype.setAttributeNode = function(attr) {
            if (attr) {
                var name = attr.name;
                if (name == "href" || name == "src" || name == "action") {
                    value = $url(attr.value);
                }
            }

            return $setAttributeNode.call(this, attr);
        };
        Element.prototype.setAttributeNodeNS = function(attr) {
            if (attr) {
                var name = attr.name;
                if (name == "href" || name == "src" || name == "action") {
                    value = $url(attr.value);
                }
            }

            return $setAttributeNodeNS.call(this, attr);
        };
    })(Node.prototype.appendChild, Node.prototype.insertBefore, Node.prototype.replaceChild,
        Element.prototype.setAttribute, Element.prototype.setAttributeNS, Element.prototype.setAttributeNode, Element.prototype.setAttributeNodeNS);
})(window);
