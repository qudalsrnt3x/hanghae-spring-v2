let index = {
    init: function () {
        // 게시물 전체 조회 함수
        $('#posts-box').empty();
        this.getPosts();

        // 게시물 저장 함수
        $('#btn-board-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });
    },

    save: function () {
        //alert('call save');

        let data = {
            title: $('#post-title').val(),
            content: $('#post-contents').val()
        };

        $.ajax({
            type: 'POST',
            url: "/posts",
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function (response) {
            //console.log(response);
            if (response['status'] === 201) {
                alert(response['message']);
                location.href = '/';
            }
            //location.href = '/auth/loginForm';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    getPosts: function (page) {
        // 1. 기존 메모 내용 지운다.

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
                              <td><a href="/posts/${id}">${title}</a> </td>
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
                        <li class="page-item ${first ? 'disabled' : ''}"> <a href="javascript:void(0)" class="page-link" onclick="index.getPosts(${number - 1})"> <span>Previous</span></a> </li>
                        <li class="page-item ${last ? 'disabled' : ''}"> <a href="javascript:void(0)" class="page-link" onclick="index.getPosts(${number + 1})"> <span>Next</span></a> </li>
                    </ul>
                `;

                $('#posts-page').append(page_html);
            }
        });
    }

}

index.init();