(function(open, document) {
    function safe_url(url) {

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

                return parts.join('/');
            }
        }

        if (url.indexOf("http://") === 0) {
            return url.replace("http://", "/");
        } else if (url.indexOf("//") === 0) {
            return url.replace("//", "/");
        } else if (url.indexOf("https://") === 0) {
            return url.replace("https://", "/s/");
        } else if (url.indexOf("/") === 0) {
            return base_host(path) + url;
        } else {
            return base_url(path) + "/" + url;
        }

    }

    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        var sUrl = safe_url(url);
        console.log(url + " redirected to " + sUrl);
        open.call(this, method, sUrl, async, user, pass);
    };

    if (MutationObserver) {
        var observer = new MutationObserver(function(mutations) {
            mutations.forEach(function (mutation) {
                console.log(mutation.type);
            });
        });

        var config = {
            attributes: true,
            childList: true,
            subtree: true,
            attributeFilter: ["href", "src", "action"] };

        observer.observe(document, config);
    }

})(XMLHttpRequest.prototype.open, document);
