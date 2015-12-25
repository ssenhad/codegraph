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

Array.prototype.distinct = function () {
    return this.filter(function (element, position, self) {
        return self.indexOf(element) == position;
    });
};

Array.prototype.flatten = function () {
    return this.concat.apply([], this);
};

Array.prototype.contains = function (element) {
    return this.indexOf(element) >= 0;
}
