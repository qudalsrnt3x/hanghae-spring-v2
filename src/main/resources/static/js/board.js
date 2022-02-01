let index = {
    init: function () {
        // 게시물 전체 조회 함수
        $('#posts-box').empty();
        this.getPosts();

        // 게시물 저장 함수
        $('#btn-board-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.save();
        });

        // 댓글 저장 함수
        $('#btn-reply-save').on('click', () => { // function(){}, ()=>{} this를 바인딩 하기 위해
            this.replySave();
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
    },

    replySave: function () {
        //alert('call save');

        let id = $('#board-id').val(); // boardId

        let data = {
            content : $('#reply-content').val()
        }

        if (data.content === '') {
            alert('댓글 내용을 입력해주세요.');
            $('#reply-content').focus();
            return;
        }

        $.ajax({
            type: 'POST',
            url: `/posts/${id}/reply`,
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function (response) {
            alert('댓글 작성이 완료 되었습니다.');
            location.href = `/posts/${id}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    deleteReply: function (postsId, replyId) {
        //console.log(postsId, replyId);

        if (confirm('정말로 삭제하시겠습니까?')) {
            $.ajax({
                type: 'DELETE',
                url: `/posts/${postsId}/reply/${replyId}`
            }).done(function (response){
                alert('댓글이 삭제되었습니다.');
                location.href = `/posts/${postsId}`
            }).
            fail(function (error) {

            });
        }
    }, // end

    // 수정 버튼 눌렀을 때, 기존 작성 내용을 textarea에 전달한다.
    // 숨길 버튼을 숨기고, 나타낼 버튼을 나타낸다.
    editReply: function (replyId) {
        index.showEdits(replyId);
        let contents = $(`#content-${replyId}`).text().trim();
        $(`#textarea-${replyId}`).val(contents);
    }, // end

    showEdits: function (replyId) {

        $(`#editarea-${replyId}`).show();
        $(`#submit-${replyId}`).show();
        $(`#cancel-${replyId}`).show();

        $(`#content-${replyId}`).hide();
        $(`#edit-${replyId}`).hide();
        $(`#delete-${replyId}`).hide();
    }, // end

    cancelReply: function (replyId) {
        $(`#editarea-${replyId}`).hide();
        $(`#submit-${replyId}`).hide();
        $(`#cancel-${replyId}`).hide();

        $(`#content-${replyId}`).show();
        $(`#edit-${replyId}`).show();
        $(`#delete-${replyId}`).show();
    },

    updateReply: function (postsId, replyId) {
        //console.log(postsId, replyId);

        let data = {
            content : $(`#textarea-${replyId}`).val().trim()
        };

        if (data.content === '') {
            alert('댓글 내용을 입력해주세요.');
            $(`#textarea-${replyId}`).focus();
            return;
        }

        $.ajax({
            type: 'PUT',
            url: `/posts/${postsId}/reply/${replyId}`,
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function (response) {
            alert('댓글 수정이 완료 되었습니다.');
            location.href = `/posts/${postsId}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });



    } //end


}

index.init();