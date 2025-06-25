import foodList from "./foodList.js";

const checkboxes = document.querySelectorAll(".checkbox");
// 재료 검색창
const $search = document.querySelector("#search");
const $autoComplete = document.querySelector(".autocomplete");
// 레시피 검색 버튼
const $searchBtn = document.querySelector("#searchBtn");
const $addedList = document.querySelector(".addedlist");
let selectedItems = []; // 선택한 항목을 저장할 배열
// 검색 버튼 클릭 시
$searchBtn.addEventListener("click", () => {
  handleSearch();
});

// 엔터키 눌렀을 때
$search.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    handleSearch();
  }
});

function handleSearch() {
  const value = $search.value.trim();
  if (value) {
    addToAddedList(value); // 선택한 항목을 추가
    $search.value = ""; // 검색어 입력란 초기화
    $autoComplete.innerHTML = ""; // 자동완성 리스트 초기화
  }
}

//---------------------------------------------------------------
// 재료 검색 자동완성
let nowIndex = 0;

$search.onkeyup = (event) => {
  // 검색어
  const value = $search.value.trim();

  // 자동완성 필터링
  const matchDataList = value
    ? foodList.filter((label) => label.includes(value))
    : [];

  switch (event.keyCode) {
    // UP KEY
    case 38:
      nowIndex = Math.max(nowIndex - 1, 0);
      break;

    // DOWN KEY
    case 40:
      nowIndex = Math.min(nowIndex + 1, matchDataList.length - 1);
      break;

    // ENTER KEY
    case 13:
      document.querySelector("#search").value = matchDataList[nowIndex] || "";

      // 초기화
      nowIndex = 0;
      matchDataList.length = 0;
      break;

    // 그외 다시 초기화
    default:
      nowIndex = 0;
      break;
  }

  // 리스트 보여주기
  showList(matchDataList, value, nowIndex);
};

const showList = (data, value, nowIndex) => {
  // 정규식으로 변환
  const regex = new RegExp(`(${value})`, "g");

  $autoComplete.innerHTML = data
    .map(
      (label, index) => `
      <div class='${nowIndex === index ? "active" : ""}'>
        ${label.replace(regex, "<mark>$1</mark>")}
      </div>
    `
    )
    .join("");
};

//---------------------------------------------------------------

// 리스트에 선택한 재료 추가
const addToAddedList = (item) => {
  if (!selectedItems.includes(item)) {
    if (!foodList.includes(item)) {
      alert(`${item}은 목록에 없습니다`);
      return;
    }
    // 중복 추가 방지
    selectedItems.push(item); // 선택한 항목을 배열에 추가
    checkboxes.forEach((checkbox) => {
      if (checkbox.value == item) {
        checkbox.checked = true;
      }
    });
    console.log(selectedItems);
    renderAddedList(); // addedlist 업데이트
  }
};

const addedListDiv = document.querySelector(".addedlist");

// Local Storage에서 재료 목록을 가져옴
const ingredients = localStorage.getItem("ingredients");
if (ingredients) {
  selectedItems = ingredients.split(/[ ,]+/);
  updateAddedList();
  checkSelectedCheckboxes();
  localStorage.removeItem("ingredients");
}

// 추가 버튼 클릭 시
document.getElementById("searchBtn").addEventListener("click", function () {
  const searchInput = document.getElementById("search");
  const ingredient = searchInput.value.trim();
  if (ingredient && !selectedItems.includes(ingredient)) {
    selectedItems.push(ingredient);
    updateAddedList();
    searchInput.value = "";
  }
});

// addedListDiv 업데이트
function updateAddedList() {
  addedListDiv.innerHTML = "";
  selectedItems.forEach((item) => {
    const itemDiv = document.createElement("div");
    itemDiv.textContent = item;
    itemDiv.className = "selectedFoods";
    itemDiv.addEventListener("click", function () {
      removeFromAddedList(item);
    });
    addedListDiv.appendChild(itemDiv);
  });
}

// 리스트에서 선택한 재료 삭제
const removeFromAddedList = (item) => {
  selectedItems = selectedItems.filter((selectedItem) => selectedItem !== item);
  checkboxes.forEach((checkbox) => {
    if (checkbox.value == item) {
      checkbox.checked = false;
    }
  });
  console.log(selectedItems);
  renderAddedList(); // addedlist 업데이트
};

// 재료 추가,삭제 표시
const renderAddedList = () => {
  $addedList.innerHTML = ""; // 기존 리스트 초기화
  selectedItems.forEach((item) => {
    const listItem = document.createElement("div");
    listItem.textContent = item;
    listItem.classList.add("selectedFoods"); // 클래스 추가
    listItem.addEventListener("click", () => {
      removeFromAddedList(item); // 항목 클릭 시 삭제
    });
    $addedList.appendChild(listItem);
  });
};

// 체크박스 상태 초기화
function checkSelectedCheckboxes() {
  checkboxes.forEach(function (checkbox) {
    if (selectedItems.includes(checkbox.value)) {
      checkbox.checked = true;
    }
  });
}

checkboxes.forEach(function (checkbox) {
  checkbox.addEventListener("change", function () {
    if (this.checked) {
      addToAddedList(this.value);
      console.log(`"${this.value}" 가 선택되어 리스트에 추가되었습니다.`);
    } else {
      removeFromAddedList(checkbox.value);
      console.log(
        `"${checkbox.value}" 가 선택 해제되어 리스트에서 제거되었습니다.`
      );
    }
  });
});

// Function to show the loader
function showLoader() {
  document.getElementById("loader").style.display = "block";
  document.getElementById("overlayForLoading").style.display="block";
  document.body.style.overflow = "hidden"; // Disable scrolling
}

// Function to hide the loader
function hideLoader() {
  document.getElementById("loader").style.display = "none";
  document.getElementById("overlayForLoading").style.display="none";
  document.body.style.overflow = "auto"; // Enable scrolling
}
// api 호출 완료하면 자동 스크롤
function scrollToResults() {
  const recipeResults = document.getElementById("recipe-results");
  recipeResults.scrollIntoView({
    behavior: "smooth",
    block: "start",
  });
}

const findRecipeBtn = document.getElementById("findRecipeBtn");
// 요리 레시피를 표시하는 div에 대한 참조 가져오기

// 선택된 체크박스 리스트에 추가, 해제되면 삭제
findRecipeBtn.addEventListener("click", getFoodRecipe);
// const recipeResults = document.getElementById("recipe-results");

function getFoodRecipe() {
  if (selectedItems.length === 0) {
    alert("재료를 입력하세요");
    return;
  }


  let apiUrl = `http://openapi.foodsafetykorea.go.kr/api/f415b345bda946528b8e/COOKRCP01/json/0/1000/`;
  showLoader();
  fetch(apiUrl)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json();
    })
    .then((data) => {
      if (data && data.COOKRCP01 && data.COOKRCP01.row) {
        const recipes = data.COOKRCP01.row;
        const filteredRecipes = recipes.filter((recipe) =>
          selectedItems.some((item) => recipe.RCP_PARTS_DTLS.includes(item))
        );
        console.log(filteredRecipes);
        displayRecipes(filteredRecipes);
        scrollToResults();
      } else {
        console.error("Unexpected data structure", data);
      }
    })
    .catch((error) => {
      console.error(
        "There has been a problem with your fetch operation:",
        error
      );
    })
    .finally(() => {
      hideLoader(); // Hide loader after fetching data
    });
}

function displayRecipes(recipes) {
  const recipesDiv = document.getElementById("recipe-results");
  recipesDiv.innerHTML = "";
  if (recipes.length === 0) {
    alert("해당 재료를 포함한 레시피가 없습니다.");
    return;
  }
  recipes.forEach((recipe, index) => {
    const recipeDiv = document.createElement("div");
    recipeDiv.className = "recipe";
    // 요리 이름 생성
    const title = document.createElement("h3");
    title.className = "food-name";
    title.textContent = recipe.RCP_NM;
    title.setAttribute("data-name", recipe.RCP_NM);
    // 요리 사진 생성
    const img = document.createElement("img");
    img.src = recipe.ATT_FILE_NO_MAIN || recipe.ATT_FILE_NO_MK;
    recipeDiv.appendChild(img);
    recipeDiv.appendChild(title);
    recipesDiv.appendChild(recipeDiv);
  });

  document.querySelectorAll(".recipe").forEach((recipeDiv) => {
    // h3 요소에 이벤트 리스너 추가
    const title = recipeDiv.querySelector("h3");
    if (title) {
      title.addEventListener("click", function () {
        console.log("제목 클릭됨");
        const recipeName = this.getAttribute("data-name");
        window.location.href = `/Wimr/recipe/getRecipeByTitle/${encodeURIComponent(recipeName)}`;
      });
    }

    // img 요소에 이벤트 리스너 추가
    const img = recipeDiv.querySelector("img");
    if (img) {
      img.addEventListener("click", function () {
        console.log("이미지 클릭됨");
        const recipeName = recipeDiv
          .querySelector("h3")
          .getAttribute("data-name");
        window.location.href = `/Wimr/recipe/getRecipeByTitle/${encodeURIComponent(recipeName)}`;
      });
    }
  });
}

// -------------------------------------------------------
// 서버로 selectedItems 배열을 보내는 함수

// function submitSelectedItems() {
//   if (selectedItems.length === 0) {
//     alert("재료를 입력해주세요.");
//     return; // 배열이 비어있으면 함수 종료
//   }

//   console.log("서버에 전송");
//   $.ajax({
//     url: "http://localhost:8080/Wimr/foodSelect",
//     type: "POST",
//     contentType: "application/json",
//     data: JSON.stringify({ items: selectedItems }), // selectedItems 배열을 JSON으로 변환하여 전송
//     success: function (response) {
//       console.log("Success:", response);
//     },
//     error: function (xhr, status, error) {
//       console.error("Error:", error);
//     },
//   });
// }

// 전송 버튼 클릭 시 selectedItems 배열을 서버로 전송
// findRecipeBtn.addEventListener("click", submitSelectedItems);
