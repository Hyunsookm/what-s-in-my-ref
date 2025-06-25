document.documentElement.style.setProperty(
  "--window-width",
  document.documentElement.clientWidth
);
document.documentElement.style.setProperty(
  "--window-height",
  document.documentElement.clientHeight
);

//index.html 애니메이션 처리 gsap
document.addEventListener("DOMContentLoaded", (event) => {
  gsap.set(".back", { opacity: 0, y: 30 });

  gsap.utils.toArray(".big-box").forEach((container) => {
    let back = container.querySelector(".back"),
      box = container.querySelector(".box"),
      tl = gsap.timeline({ paused: true }),
      t2 = gsap.timeline({ paused: true });

    tl.to(back, { opacity: 1, y: 0, ease: "none" });
    t2.to(box, { scale: 1.05, duration: 1 });

    box.addEventListener("mouseenter", () => tl.timeScale(5).play());
    box.addEventListener("mouseenter", () => t2.timeScale(3).play());
    box.addEventListener("mouseleave", () => tl.timeScale(3).reverse());
    box.addEventListener("mouseleave", () => t2.timeScale(3).reverse());
  });
});

//웹페이지 이동
function redirectToPage(str) {
  window.location.href = str;
}

/*---------------------------------------
side menu bar 
----------------------------------------*/
var menu_bar = document.getElementById("mobile-nav-toggle");

document.addEventListener("DOMContentLoaded", (event) => {
  let x_top = menu_bar.querySelector(".x-top"),
    x_middle = menu_bar.querySelector(".x-middle"),
    x_bottom = menu_bar.querySelector(".x-bottom"),
    sidebar = document.getElementById("sidebar"),
    width = window.innerWidth;

  gsap.set(sidebar, { x: width });

  tl = gsap.timeline({ paused: true });
  tl2 = gsap.timeline({ paused: true });

  tl.to(x_top, { y: 8.1, rotateZ: 45, ease: "none" });
  tl.to(x_middle, { width: 0, ease: "none" }, 0);
  tl.to(x_bottom, { y: -8.1, rotateZ: -45, ease: "none" }, 0);
  tl2.to(sidebar, { x: "-=" + width, ease: "none" }, 0);

  let isMenuOpen = false;

  menu_bar.addEventListener("click", () => {
    if (!isMenuOpen) {
      tl.timeScale(10).play();
      tl2.timeScale(3).play();
    } else {
      tl.timeScale(10).reverse();
      tl2.timeScale(3).reverse();
    }
    isMenuOpen = !isMenuOpen;
  });
});

//폼 제출 function
function form_submit(formId){
  form=document.getElementById(formId);
  form.submit();
}

//엔터키로 버튼 클릭
function enterPushButton(event){
  if(event.keyCode===13){
    document.getElementById("search-btn").click();
  }
}
document.getElementById("search-form").addEventListener("keypress",enterPushButton);

//검색 재료 넘기기
function searchIngredients() {
  const ingredients = document.getElementById('ingredients').value.trim();
  if (ingredients) {
      localStorage.setItem('ingredients', ingredients);
      form_submit('search-form');
    document.getElementById("search-form").submit();
  } else {
      alert('재료를 입력하세요.');
  }
}


//레시피 화면 전환
function redirectToRecipePage(element) {
  var recipeno = element.getAttribute('data-recipeno');
  window.location.href = '/Wimr/recipe/which/' + encodeURIComponent(recipeno);
}