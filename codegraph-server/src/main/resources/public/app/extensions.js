/*
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
