/**
 * converts a timestamp to a readable date
 * @param timestamp
 * @return {string} day.month.year
 */
export function formatDate(timestamp) {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    const month = months[date.getMonth()];
    const day = date.getDate();

    return day + ' ' + month + ' ' + year;
}