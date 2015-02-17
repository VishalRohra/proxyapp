(function(window, document) {
    var path = window.location.pathname;
    with (window) {
        _location = {
            hash: location.hash,
            host: location.host,
            hostname: location.hostname,
            href: location.href,
            origin: location.origin,
            pathname: location.pathname,
            port: location.port,
            protocol: location.protocol,
            assign: function(url) {
                location.assign($url(url));
            },
            reload: function(bool) {
                location.assign(bool);
            },
            replace: function(url) {
                location.replace($url(url));
            },
            search: function(str) {
                location.search(str);
            },
            toString: function() {
                return location.toString();
            }
        };
    }

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
        return typeof url != "string" || url.indexOf("/s/") === 0 || url.indexOf(baseHost) === 0 || url.indexOf("data:image") === 0 || url.indexOf("about:blank") === 0 || url.indexOf("#") === 0 || url.match(/^\/[\w\.\:\@\-]{3,}\.[\w]{2,5}(:[0-9]+)?/g);
    }

    function $url(url) {
        if (!is_safe_url(url)) {
            return safe_url(url);
        } else {
            return url;
        }
    }

    function is_url_prop(name) {
        return name == "src" || name == "href" || name == "action";
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
            node = unwrap(node);
            protect_node(node);
            return $appendChild.call(this, node);
        };
        Node.prototype.insertBefore = function(newElement, referenceElement) {
            node = unwrap(newElement);
            protect_node(newElement);
            return $insertBefore.call(this, newElement, referenceElement);
        };
        Node.prototype.replaceChild = function(newChild, oldChild) {
            node = unwrap(newChild);
            protect_node(newChild);
            return $replaceChild.call(this, newChild, oldChild);
        };
        Element.prototype.setAttribute = function(name, value) {
            if (is_url_prop(name)) {
                value = $url(value);
            }

            return $setAttribute.call(this, name, value);
        };
        Element.prototype.setAttributeNS = function(namespace, name, value) {
            if (is_url_prop(name)) {
                value = $url(value);
            }

            return $setAttributeNS.call(this, namespace, name, value);
        };
        Element.prototype.setAttributeNode = function(attr) {
            if (attr) {
                var name = attr.name;
                if (is_url_prop(name)) {
                    value = $url(attr.value);
                }
            }

            return $setAttributeNode.call(this, attr);
        };
        Element.prototype.setAttributeNodeNS = function(attr) {
            if (attr) {
                var name = attr.name;
                if (is_url_prop(name)) {
                    value = $url(attr.value);
                }
            }

            return $setAttributeNodeNS.call(this, attr);
        };
    })(Node.prototype.appendChild, Node.prototype.insertBefore, Node.prototype.replaceChild,
        Element.prototype.setAttribute, Element.prototype.setAttributeNS,
        Element.prototype.setAttributeNode, Element.prototype.setAttributeNodeNS);

    (function ($createElement, $createElementNS) {
        document.createElement = function(tagName) {
            var elm = $createElement.call(this, tagName);
            if (tagName == "img") {
                var desc = {
                    set: function (newVal) {
                        desc.value = $url(newVal);
                        Object.defineProperty(elm, "src", desc)
                    },
                    get: function () {
                        return desc.value;
                    },
                    value: "", writable: false, enumerable: true, configurable: true
                };
                Object.defineProperty(elm, "src", desc)
            }

            return elm;
        };
    })(document.createElement, document.createElementNS);


    if (MutationObserver) {
        var observer = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                console.log(mutation.type);
            });
        });

        var config = { attributes: true, characterData: true, subtree: true, attributeFilter: ["src", "href", "action"] };
        observer.observe(document, config);
    }

    var wrap = function (object) {
        console.log("stub " + object.name);

        var _origin;

        function copy_prop(name, from, to) {
            if (from[name] != undefined && from[name] != to[name]) {
                if (is_url_prop(name)) {
                    var url = $url(from[name]);
                    to[name] = url;
                    from[name] = url;
                } else {
                    try {
                        to[name] = from[name];
                    } catch (e) {
                        //console.log(e);
                    }
                }
            }
        }

        function updateProps(stub) {
            for(prop in _origin) {
                if (typeof _origin[prop] != 'function') {
                    copy_prop(prop, stub, _origin);
                }
            }
        }

        var _returnOrigin = function() {
            updateProps(this);
            return _origin;
        };

        if (typeof object == 'function') {
            return function() {
                var $this = this;
                _origin = new object(arguments);
                for (prop in _origin) {
                    if (typeof _origin[prop] == 'function') {
                        var originalMethod = _origin[prop];
                        var method = function() {
                            console.log("method " + prop + " is called");
                            updateProps(this);
                            return originalMethod.apply(_origin, arguments);
                        }
                    } else {
                        var val = _origin[prop];
                        if (val != 0 && val != "" && val != undefined) {
                            copy_prop(prop, _origin, this);
                        }
                    }
                }

                this._returnOrigin = _returnOrigin;

            };
        } else if (typeof object == 'object') {

        }
    };

    var unwrap = function (object) {
        if (object instanceof wImage) {
            return object._returnOrigin();
        } else {
            return object;
        }
    };

    window.wrap = wrap;
    window.unwrap = unwrap;

    // todo intercept an insertion of this object into DOM
    window.Image = wImage = wrap(window.Image);

    //todo intercept document.createElement()

})(window, document);
