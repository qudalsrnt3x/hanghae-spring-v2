$(document).ready(function (){
    getPosts();
})

function getPosts(page) {
    // 1. 기존 메모 내용 지운다.
    $('#posts-box').empty();
    $('#posts-page').empty();

    if (page === undefined) {
        page = 0;
    }
    // 2. 게시글 목록 불러오기
    $.ajax({
        type: 'GET',
        url: `/posts?page=${page}`,
        success: function (response) {
            //console.log(response);
            for (let i = 0; i < response['content'].length; i++) {
                let board = response['content'][i];

                let id = board['id'];
                let title = board['title'];
                let author = board['user']['username'];
                let createdAt = board['createdAt'];

                let temp_html = `<tr>
                              <th scope="row">${id}</th>
                              <td>${author}</td>
                              <td><a href="javascript:void(0)">${title}</a> </td>
                              <td>${createdAt}</td>
                            </tr>`;

                $('#posts-box').append(temp_html);
            }

            // 페이징 처리
            let number = response['number'];
            let first = response['first'];
            let last = response['last'];


            let page_html = `
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${first ? 'disabled':''}"> <a href="javascript:void(0)" class="page-link" onclick="getPosts(${number - 1})"> <span>Previous</span></a> </li>
                        <li class="page-item ${last ? 'disabled':''}"> <a href="javascript:void(0)" class="page-link" onclick="getPosts(${number + 1})"> <span>Next</span></a> </li>
                    </ul>
                `;

            $('#posts-page').append(page_html);
        }
    });
}

let index = {
    init: function (){
        $('#btn-board-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });
    },

    save: function (){
        //alert('call save');

        let data = {
            title: $('#post-title').val(),
            content: $('#post-contents').val()
        };

        // ajax 호출시 default가 비동기 호출
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 Insert 요청
        // ajax ㄱㅏ 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바스크립트 오브젝트로 변환해준다.
        $.ajax({
            // 회원가입 수행요청
            type: 'POST',
            url : "/posts",
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function (response){
            console.log(response);
            if (response['status'] === 201) {
                alert(response['message']);
                location.href = response['location'];
            }
            //location.href = '/auth/loginForm';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }

}

index.init();