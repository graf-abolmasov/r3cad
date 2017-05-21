define([
    'dojo/request',
    'dojo/_base/declare',
    'dojo/dom',
    'dojo/_base/lang',
    'put-selector/put'
], function(req, declare, dom, lang, put) {

    function blockScroll() {
        d3.event.preventDefault();
    }

    return declare(null, {

        loadingText: 'Drawing is loading.. Please, wait...',

        targetUrl: '',

        _refreshing: false,

        _drawing: null,

        constructor: function (props) {
            lang.mixin(this, props || {});
            this.domNode = dom.byId(this.id);
        },

        refresh: function() {
            if (this._refreshing) {
                return;
            }

            var self = this;
            var containerDiv = this.domNode;

            this._refreshing = true;
            put(containerDiv, '.loading');
            containerDiv.innerText = this.loadingText;

            req(this.targetUrl).then(
                function (data) {
                    self._refreshing = false;
                    containerDiv.innerHTML = data;

                    var container = d3.select('#' + self.id)
                        .on("mousewheel", blockScroll)
                        .on("DOMMouseScroll", blockScroll);

                    container.selectAll('svg')
                        .attr("width", "100%")
                        .attr("height", "100%")
                        .attr("pointer-events", "all")
                        .call(d3.behavior.zoom().on("zoom", function () {
                            self._drawing.attr("transform", "translate(" + d3.event.translate + ")" + " scale(" + d3.event.scale + ")");
                        }));

                    self._drawing = container.select('.viewport');
                    put(containerDiv, '!loading');
                },
                function (error) {
                    self._refreshing = false;
                    containerDiv.innerHTML = data;
                    put(containerDiv, '!loading');
                });
        },

        startup: function() {
            this.refresh();
        }
    })

});