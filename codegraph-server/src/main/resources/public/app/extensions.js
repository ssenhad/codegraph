'use strict';

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
};
