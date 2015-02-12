(function(open, appendChild) {
    var path = window.location.pathname;

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
            parts.remove(parts.length - 1);

            return "/" + parts.join('/') + "/";
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

    function check_attribute(node, attrName) {
        if (node instanceof Element && node.hasAttribute(attrName)) {
            url = node.getAttribute(attrName);
            if (!is_safe_url(url)) {
                var safeUrl = safe_url(url);
                node.setAttribute(attrName, safeUrl);
                console.log("protecting url " + url + " safe_url = " +safeUrl);
            } else {
                console.log("url is safe " + url);
            }
        }
    }

    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        if (is_safe_url(url)) {
            var sUrl = url;
        } else {
            var sUrl = safe_url(url);
            console.log(url + " redirected to " + sUrl);
        }
        open.call(this, method, sUrl, async, user, pass);
    };

    Element.prototype.appendChild = function(node) {
        check_attribute(node, "href");
        check_attribute(node, "src");
        check_attribute(node, "action");
        appendChild.call(node);
    }

    /*
    if (MutationObserver) {
        var observer = new MutationObserver(function(mutations) {
            mutations.forEach(function (mutation) {
                for (var i = 0; i < mutation.addedNodes.length; ++i) {
                    var node = mutation.addedNodes[i];
                    check_attribute(node, "href");
                    check_attribute(node, "src");
                    check_attribute(node, "action");
                }
            });
        });

        var config = {
            attributes: true,
            childList: true,
            subtree: true,
            attributeFilter: ["href", "src", "action"] };

        observer.observe(document, config);
    }*/

})(XMLHttpRequest.prototype.open, Element.prototype.appendChild);
