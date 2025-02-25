async function get1(bno) {

    const result = await axios.get(`/replies/list/${bno}`)

// 밑 console.log는 web page에서 작업 관리자에서는 나오고, intelliJ에서는 안나옴. console.log != log.info()
// result 객체에 담기는 애들은 {data: {…}, status: 200, statusText: '', headers: r, config: {…}, …}
// 애네들이 배열로 담기고, 또 data라고 하는 애가 {page: 1, size: 10, total: 100, start: 1, end: 10, …}라는
// 배열을 가진다. >>>>>>>>>>>>>>>>>>>>>>>> result는 다차원 배열을 담은 객체다.

// console.log(result)


//    return result.data

      return result
}

async function getList({bno, page, size, goLast}) {

    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    if(goLast) {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`, replyObj)

    return response.data
}