'use strict';

String.prototype.apply = function (o) {
    return this.replace(/{([^{}]*)}/g,
        function (a, b) {
            var r = o[b];
            return typeof r === 'string' || typeof r === 'number' ? r : a;
        }
    );
};

Array.prototype.remove = function (element) {
    var index = this.indexOf(element);
    this.splice(index, 1);
};
