(function(open) {
    function safe_url(url) {

        var path = window.location.pathname;
        function base_host(path) {
            parts = path.split("/");
            if (parts[0] == "s") {
                return "/s/" + parts[1];
            } else {
                return parts[0];
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
            return base_host(path) + "/" + url;
        } else {
            return base_url(path) + "/" + url;
        }

    }

    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        console.log(url);
        open.call(this, method, safe_url(url), async, user, pass);
    };

})(XMLHttpRequest.prototype.open);
