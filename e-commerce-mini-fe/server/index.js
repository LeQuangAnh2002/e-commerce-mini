/*
  This is our backend server.
  Replace YOUR_IMAGEKIT_URL_ENDPOINT, YOUR_IMAGEKIT_PUBLIC_KEY,
  and YOUR_IMAGEKIT_PRIVATE_KEY with actual values
*/
const express = require('express');
const app = express();
const ImageKit = require('imagekit');

const imagekit = new ImageKit({
    urlEndpoint: 'https://ik.imagekit.io/mduf5wsgw',
    publicKey: 'public_BFygarEYPUWnf8M+hyAUqGv2Kq8=',
    privateKey: 'private_YeWAhXy+st0dhSzRffCt4tdfmQI='
});

// allow cross-origin requests
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers",
        "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.get('/auth', function (req, res) {
    var result = imagekit.getAuthenticationParameters();
    res.send(result);
});

app.listen(3001, function () {
    console.log('Live at Port 3001');
});