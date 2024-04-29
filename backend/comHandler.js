// useless file for now since we cannot get it to connect to a public server

let socket = "";

function connect()
{
    const connectionVar = 'ws://192.168.1.194:5001';
    socket = new WebSocket(connectionVar);

    socket.addEventListener('open', function (event) 
    {
        console.log('Connected to server!', event);
    });

    socket.addEventListener('close', function (event) 
    {
        console.log('Disconnected from server', event);
    });

    socket.addEventListener('error', function (event) 
    {
        console.error("Error, couldn't establish connection", event);
    });
}

function write(data)
{
    try 
    {
        if (socket.readyState === WebSocket.OPEN) 
        {
            socket.send(data);
        } else 
        {
            console.error('WebSocket connection not open.');
        }
    } catch (e) 
    {
        console.error("Error sending data:", e);
    }
}

function read(callback) 
{
    socket.addEventListener('message', function(event) 
    {
        callback(event.data);
    });
    // data = "";
    // socket.onmessage = function(event) 
    // {
    //     data = event.data;
    // }

    // return data;
}

function encrypt(data, key)
{
    console.log(key);
    let xoredChars = [];

    for (let i=0; i<data.length; i++) 
    {
        xoredChars[i] = String.fromCharCode(inputChars[i].charCodeAt(0) ^ key);
    }

    return xoredChars.join('');
}

function decrypt(data, key)
{
    console.log(key);
    let xoredChars = [];

    for (let i=0; i<data.length; i++)
    {
        xoredChars[i] = String.fromCharCode(inputChars[i].charCodeAt(0) ^ key);
    }

    return xoredChars.join('');
}