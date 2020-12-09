const walk = require('walkdir');
const fs = require('fs');
const async = require("async")
const {performance} = require('perf_hooks')

let count = 0;
const countLines = (file, cb) => { fs.createReadStream(file).on('data', function(chunk) {
				let lines = chunk.toString('utf8')
                .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
                .length-1;
				count +=lines
				// console.log(file, lines)
            }).on('end', function() {
                cb()
            }).on('error', function(err) {
                cb()
			});

        }

const paths = walk.sync('PAM08');

const pathTasks = paths.map(path => (cb) => countLines(path, cb))

const callSync = async () => {
    const t1 = performance.now()
    async.waterfall(pathTasks, () => {
        const t2 = performance.now()
		console.log(count);
        console.log(t2 - t1);
    })
}

const callAsync = async () => {
    const t1 = performance.now()
    async.parallel(pathTasks, () => {
        const t2 = performance.now()
		console.log(count);
        console.log(t2 - t1);
    })
}

// callSync()
callAsync()