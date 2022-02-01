let index = {
    init: function () {
        // 게시물 전체 조회 함수
        this.getPosts();

        // 게시물 저장 함수
        $('#btn-board-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });

        let username = [[${principal.user.id}]]
        console.log(username);
    },

    save: function () {
        //alert('call save');

        let data = {
            title: $('#post-title').val(),
            content: $('#post-contents').val()
        };

        $.ajax({
            // 회원가입 수행요청
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
                              <td><a href="javascript:void(0)" onclick="index.detail(${id})">${title}</a> </td>
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
    },

    detail: function (id) {

        $('#posts-list').hide();
        $('#posts-detail').show();

        // 상세 게시물 조회
        $.ajax({
            type: 'GET',
            url: `/posts/${id}`,
        }).done(function (response) {
            //console.log(response);
            //location.href = '/auth/loginForm';
            $('#detail-box').empty();

            let title = response['title'];
            let author = response['user']['username'];
            let contents = response['content'];
            let createdAt = response['createdAt'];

            let temp_html = `<div class="row">
                                  <div class="col-md-6" >
                                    <h2 class="pl-1">${title}</h2>
                                  </div>
                                  <div class="col-md-6 text-right">
                                    <button  class="btn btn-warning" type="button" onclick="index.showList()">수정</button>
                                    <button  class="btn btn-danger" type="button" onclick="index.showList()">삭제</button>
                                    <button class="btn btn-primary" type="button" onclick="index.showList()">목록</button>
                                  </div>
                                </div>
                                <div class="row"> 
                                  <div class="col-md-12">
                                    <div class="card">
                                      <div class="card-header">작성자 : ${author}</div>
                                      <div class="card-header">작성일 : ${createdAt}</div>
                                      <div class="card-body h-50">
                                        <h4>${contents}</h4>
                                      </div>
                                    </div>
                                  </div>
                                </div>`;

            $('#detail-box').append(temp_html);

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    showList: function () {
        $('#posts-detail').hide();
        $('#posts-list').show();
    }

}

index.init();