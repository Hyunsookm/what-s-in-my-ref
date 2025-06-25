
//레시피 목록 펼치기 + 접기
document.querySelectorAll('button[id^="toggleButton"]').forEach(button => {
    button.addEventListener('click', () => {
        const targetGrid = document.getElementById(button.getAttribute('data-target'));
        const hiddenItems = targetGrid.querySelectorAll('.shape.hidden');
        const visibleItems = targetGrid.querySelectorAll('.shape:not(.hidden)');
        
        if (hiddenItems.length > 0) {
            hiddenItems.forEach(item => {
                setTimeout(() => {
                    item.classList.remove('hidden');
                }, 50); // 지연 시간을 200밀리초로 설정
            });
            button.textContent = '접기';
        } else {
            visibleItems.forEach((item, index) => {
                if (index >= 3) {
                    setTimeout(() => {
                        item.classList.add('hidden');
                    }, 50); // 지연 시간을 200밀리초로 설정
                }
            });
            button.textContent = '더 보기';
        }
    });
});

//수정 유효성 검사
function form_check() {
    var form=document.getElementById("edit-profile-form");

    let nameError=document.getElementById("nameError"),
        emailError=document.getElementById("emailError"),

        emailCheck=/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/,
        nameCheck=/^[0-9a-zA-Z가-힣]{3,10}$/,
        error=false;

    if(!nameCheck.test(form.name.value)){                           //닉네임 입력이 없을 때
        nameError.style.display="block";
        nameError.style.visibility="visible";
        error=true;
    }else{nameError.style.display="none"; nameError.style.visibility="hidden";}
    
    if(!emailCheck.test(form.email.value)){   //이메일 형식이 다를 때
        emailError.style.display="block";
        emailError.style.visibility="visible";
        error=true;
    }else{emailError.style.display="none"; emailError.style.visibility="hidden";}
    
    if(error==true){    //에러 존재시 submit x
        return false;
    }

    form.submit();
    return true;
}

