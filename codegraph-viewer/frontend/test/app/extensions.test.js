require('../../app/extensions');

describe('Array extensions', function () {
    var array;
    beforeEach(function () {
        array = ['a', 'b', 'b', 'c', 123];
    });

    describe('distinct elements', function () {
        it('returns an array containing only the distinct elements', function () {
            var distincts = array.distinct();

            expect(distincts).toEqual(['a', 'b', 'c', 123]);
        });
    });

    describe('removing elements', function () {
        it('removes a given element', function () {
            array.remove('a');

            expect(array).toEqual(['b', 'b', 'c', 123]);
        });

        /*
        it('removes all occurences of a given element', function () {
            array.removeAll('b');

            expect(array).toEqual(['a', 'c', 123]);
        });
        */
    });
});
