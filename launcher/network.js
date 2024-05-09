const Url = require('node:url');
const https = require('https');
const fs = require('fs-extra');
const http = require('http');

/**
 * @param {Url} url The URL to download from. Can be either http or https
 * @param {String} fileName the file name of the downloaded content. You have to add the extension and full filepath!
*/
function downloadFile(url, fileName) {
    let file = fs.createWriteStream(fileName);
    if (url.protocol === 'http:') http.get(url, (res) => res.pipe(file));
    if (url.protocol === 'https:') https.get(url, (res) => res.pipe(file));
    file.on("finish", () => file.close());
}

module.exports = { downloadFile }