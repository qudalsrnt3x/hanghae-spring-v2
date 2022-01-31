$(document).ready(function (){
    getPosts();
})

function getPosts() {
    // 1. 기존 메모 내용 지운다.
    $('#posts-box').empty();
    // 2. 게시글 목록 불러오기
    $.ajax({
        type: 'GET',
        url: '/posts',
        success: function (response) {
            console.log(response);
        }
    })
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