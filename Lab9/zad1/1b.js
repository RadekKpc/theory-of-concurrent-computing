var async = require('async');

function first(callback) {
    callback(null, 1);
}

function last(number, callback){
    console.log("DONE")
    callback(null,1)
  }

function task(number, callback) {
    console.log(number)
    callback(null,number + 1)
  }

async function loop(times){
    execTable = [first]
    for(i=0;i<times;i++){
        for(j=0;j<3;j++){
        execTable.push(task)
        }
        execTable.push(last)
    }

    async.waterfall(execTable, function (err, result) {
    });
}

loop(4)