//댓글의 입력 유무 체크
function comment_check(element){
    var commentContent = document.getElementById("commentContentInput").value;
    var recipeNo=element.getAttribute("data-recipeno");
    var url="/Wimr/recipe/"+encodeURIComponent(recipeNo);
    var formData = new FormData();
    formData.append("comment", commentContent);

    if(commentContent.trim()===''){
        alert("댓글 내용을 입력해주세요.")
        return false;
    }
    
    fetch(url, {
        method: 'POST',
        body: formData,
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // 서버가 JSON 응답을 보내는 경우
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        if (data.errorMessage) {
            // 로그인 페이지로 리디렉션
            alert(data.errorMessage);
            window.location.href = data.redirect;
        }else{// 성공적으로 즐겨찾기가 완료되었을 때의 처리
        location.reload();
        console.log(data.message);} // 예시: { message: "Scrap successful!" }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

//글자 수 출력
document.addEventListener('DOMContentLoaded', function () {
    let textarea = document.getElementById('commentContentInput'),
        charCount = document.getElementById('char-count');

    textarea.addEventListener('input', function () {
        let currentLength = textarea.value.length,
            maxLength = textarea.getAttribute('maxlength');
        charCount.textContent = `${currentLength}/${maxLength}`;
        if(currentLength>=maxLength){
            charCount.style.color="red";
        }
        else{
            charCount.style.color="grey";
        }
    });
});


//스크랩 1=스크랩 0=언스크랩
function scrapRecipe(button, bool) {
    const recipeNo = button.getAttribute('data-recipeno');
    var url="";
    if(bool==1){
        url = `/Wimr/scrap?recipeNo=`+encodeURIComponent(recipeNo);}
    else{
        url = `/Wimr/unscrap?recipeNo=`+encodeURIComponent(recipeNo);
    }

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // 서버가 JSON 응답을 보내는 경우
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        if (data.errorMessage) {
            // 로그인 페이지로 리디렉션
            alert(data.errorMessage);
            window.location.href = data.redirect;
        }else{// 성공적으로 즐겨찾기가 완료되었을 때의 처리
        location.reload();
        console.log(data.message);} // 예시: { message: "Scrap successful!" }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}



//좋아요 1=like, 0=unlike
function likeRecipe(button,bool) {
    const recipeNo = button.getAttribute('data-recipeno');
    var url="";
    if(bool==1){
        url = `/Wimr/like?recipeNo=`+encodeURIComponent(recipeNo);}
    else{
        url = `/Wimr/unlike?recipeNo=`+encodeURIComponent(recipeNo);
    }

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // 서버가 JSON 응답을 보내는 경우
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        if (data.errorMessage) {
            // 로그인 페이지로 리디렉션
            alert(data.errorMessage);
            window.location.href = data.redirect;
        }else{// 성공적으로 즐겨찾기가 완료되었을 때의 처리
        location.reload();
        console.log(data.message);} // 예시: { message: "Scrap successful!" }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}