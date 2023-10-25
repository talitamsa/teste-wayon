
const PROXY_CONFIG = [{
  context: ['/transferencias'],
  target: 'http://localhost:1515/',
  secure: false,
  logLevel: 'debug'
}];

module.exports = PROXY_CONFIG;

