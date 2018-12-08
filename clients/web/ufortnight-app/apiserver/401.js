const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('db.1.json')
const middlewares = jsonServer.defaults()

server.use(middlewares)
server.use((req, res, next) => {
 if (true) { // auth logic
   res.sendStatus(401)
 } 
})
server.use(router)
server.listen(3401, () => {
  console.log('JSON Server is running on port 3401')
})
