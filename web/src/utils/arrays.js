export function contains(array, value) {
    let i = array.length;
    while (i--) {
        if (array[i] === value) {
            return true;
        }
    }
    return false;
}