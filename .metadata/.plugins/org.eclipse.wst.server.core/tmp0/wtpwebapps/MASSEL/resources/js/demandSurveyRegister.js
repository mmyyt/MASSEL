document.getElementById('thumbnailArea').addEventListener('click', function(){
    document.getElementById('thumbnailFile').click();
});

const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$", "i"); // 이미지 파일, i = 대소문자 구분 X

//썸네일 이미지 불러오기
function showThumbnailImg(event){
    const thumbnailArea = document.getElementById('thumbnailArea');

    //기존 이미지 비우기
    thumbnailArea.innerHTML = '';

    const file = event.target.files[0];
    
    //파일이 없으면 종료(파일이 선택되지 않았을경우 종료)
    if(!file){
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }
    //이미지 파일만 업로드할 수 있게
    if(!regExpImg.test(file.name)){
        alert("이미지 파일만 업로드할 수 있습니다.");
        event.target.value = "";
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }

    const reader = new FileReader();
    reader.onload = function(event){
        const img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        img.style.width = "100%"; 
        img.style.height = "100%";
        img.style.objectFit = "cover"; 
        thumbnailArea.appendChild(img);
    };
    if(file){
        reader.readAsDataURL(file);
    }
}

//@@@@@@@@@@@상품 추가하기
//상품을 담을 배열
let productList = [];
let count = 0; //class이름이랑 배열 index를 위한 count

document.getElementById("addList").addEventListener('click', ()=>{

    let name = document.getElementById("dpname").value;
    let price = document.getElementById("dpprice").value;

    if(name === '' || price === ''){
        alert("값을 입력해주세요.");
        return false;
    }

    productList.push({dpname : name, dpprice : price});

    //@@class이름 만들기
    let dsList = document.createElement("div");
    dsList.classList.add(`dsList${count}`, 'dsListClass');

    let nameDiv = document.createElement("div");
    nameDiv.textContent = name;
    nameDiv.classList.add(`dpname${count}`, 'dpnameClass');

    let priceDiv = document.createElement("div");
    priceDiv.textContent = price;
    priceDiv.classList.add(`dpprice${count}`, 'dppriceClass');

    //상품명 / 가격 text

    let nameSpan = document.createElement("div");
    nameSpan.textContent = "상품명";

    let priceSpan = document.createElement("div");
    priceSpan.textContent = "가격";

    nameSpan.appendChild(nameDiv);
    priceSpan.appendChild(priceDiv);
    //@@class 이어붙이기
    dsList.appendChild(nameSpan);
    dsList.appendChild(priceSpan);

    //@@@@@수정버튼, 삭제버튼 만들고 이어붙이기
    let editBtn = document.createElement("button");
    editBtn.classList.add(`editBtn${count}`, 'editBtn');
    editBtn.textContent = "수정";
    let delBtn = document.createElement("button");
    delBtn.classList.add(`delBtn${count}`, 'delBtn');
    delBtn.textContent = "X";

    dsList.appendChild(editBtn);
    dsList.appendChild(delBtn);


    document.querySelector('.dsProductList').appendChild(dsList);

    //@@value 값 비워주기
    document.getElementById("dpname").value = "";
    document.getElementById("dpprice").value = "";


    //@@@@@@삭제버튼
    document.querySelector(`.delBtn${count}`).addEventListener('click', (event)=>{
        event.preventDefault(); //기본폼 제출방지

        let delBtnClassName = event.target.className; 
        let delBtnIndex = delBtnClassName.match(/\d+/);

        //productList에서 삭제
        deleteProduct(delBtnIndex);

        //삭제하면서 해당 class(번호)도 div에서 삭제해야됨.
        let dsListDiv = event.target.parentElement; //delBtn부모는 dsList임
        dsListDiv.remove();     

    });

    let isEditing = false;
    let nameInput, priceInput; //전역변수로 선언해서 if문에서도 값 받을수있게

    document.querySelector(`.editBtn${count}`).addEventListener('click',(event)=>{
        event.preventDefault(); // 기본 폼 제출 방지
        //index 구하기
        let editBtnClassName = event.target.className;
        let editBtnIndex = editBtnClassName.match(/\d+/);

        let divName = document.querySelector(`.dpname${editBtnIndex}`);
        let divPrice = document.querySelector(`.dpprice${editBtnIndex}`);
        
        //현재 div에 있는 값 가져오기
        let currentName = divName.textContent;
        let currentPrice = divPrice.textContent;

        if(isEditing){

            //현재 input창에 있는 값 가져오기
            let newName = nameInput.value;
            let newPrice = priceInput.value; 

            //nameInput에 있는 값 리스트에 반영하기(제품수정 함수 호출)
            editProduct(editBtnIndex, newName, newPrice);

            //nameInput 없애기
            nameInput.remove();
            priceInput.remove();

            //div에 새로운 값 넣기
            divName.textContent = newName;
            divPrice.textContent = newPrice;
        }else{

            // 1. div에 있는 값 없애주기
            divName.textContent = "";
            divPrice.textContent = "";

            //2. input창 만들고 현재입력된 값 넣고나서 div에 이어붙여주기
            nameInput = document.createElement("input");
            nameInput.classList.add(`nameInput${editBtnIndex}`, 'nameInput');
            nameInput.value = currentName;

            priceInput = document.createElement("input");
            priceInput.classList.add(`priceInput${editBtnIndex}`, 'priceInput');
            priceInput.value = currentPrice;

            divName.appendChild(nameInput);
            divPrice.appendChild(priceInput);

        }

        isEditing = !isEditing;
    })
    //index 증가시키기
    count++;

})

//상품 삭제 기능
function deleteProduct(delBtnIndex){
    delete productList[delBtnIndex];


}

//상품 수정 기능
function editProduct(editBtnIndex, newName, newPrice){
    productList[editBtnIndex].dpname = newName;
    productList[editBtnIndex].dpprice = newPrice;
}

//@@@@@productList controller로 전송
function sendProductList(){

    try {
        $.ajax({
            url: "/demandSurvey/productListRegister",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(productList.filter(item => item !== undefined)),
            success: function (response) {
                // 응답 처리 생략 (필요하지 않으므로)
            },
            error: function (error) {
                console.error('에러 발생:', error);
            }
        });
    } catch (error) {
        console.error('에러 내용:', error); // 예외 처리
    }

}

//summernote
$(document).ready(function() {
	$('#summernote').summernote({
		  lang: "ko-KR",  		//한글
		  height: 500,    		// 에디터 높이 설정
		  minHeight: null,      // 최소 높이
		  maxHeight: null,      // 최대 높이
		  //focus: true,          // 에디터 로딩후 포커스를 맞출지 여부
		  placeholder: '작성된 내용이 부적절하다고 판단될 시 무통보 삭제 될 수 있습니다.',	//placeholder 설정
          //콜백함수
          callbacks : {
        	  onImageUpload : function (files, editor, welEditable){
        		  for(var i = files.length - 1; i>=0; i--){
        			  imageUpload(files[i], this);
        		  }
        	  }
          }
	});
});

function imageUpload(file, el){
    var formData = new FormData();
    formData.append('file', file);

$.ajax({
    data: formData,
    type: "POST",
    dataType: "JSON",
    url: '/demandSurvey/tmp',
    contentType: false,
    processData: false,
    enctype: 'multipart/form-data',
    success: function(data) {
        if (data.responseCode === 'success') {
            $(el).summernote('insertImage', data.url);
        } else {
            console.log('이미지 업로드 실패 : ', data);
        }
    },
    error: function(jqXHR, textStatus, errorThrown) {
        console.error("AJAX 요청 실패: " + textStatus + ", " + errorThrown);
        console.log(jqXHR.responseText);
    }
});
}

//기간설정부분
$(function(){
    $("#startDay").datepicker({
        dateFormat : "yy-mm-dd",
        minDate : 0, //오늘부터 선택 가능
        //maxDate : "+3M", //오늘부터 3달까지만 선택 가능
        showAnim : "fadeIn", //선택될 때 애니메이션 효과
        //changeMonth : true, //드롭다운으로 선택 가능하게
        //changeYear : true,
        yearSuffix : "년",
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
        monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
        dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
      });
});

$(function(){
  $("#endDay").datepicker({
      dateFormat : "yy-mm-dd",
      minDate : 0, //오늘부터 선택 가능
      maxDate : "+3M", //오늘부터 3달까지만 선택 가능
      showAnim : "fadeIn", //선택될 때 애니메이션 효과
      //changeMonth : true, //드롭다운으로 선택 가능하게
      //changeYear : true,
      yearSuffix : "년",
      monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
      monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
      dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
      dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
  });
});

//startHour

const startHourSelect = document.getElementById("startHour");
const endHourSelect = document.getElementById("endHour");

for(let i=0; i<24; i++){
  //시간 두자리로 하기 위해 문자 채우기
  //적용할문자열.(채울길이, 채울문자)
  //padStart는 앞부분, padEnd는 뒤를 채움
  const hour = String(i).padStart(2, '0');
  const startOption = document.createElement("option");
  startOption.value = hour;
  //option.textContent = hour+"시";
  startOption.textContent = hour;
  startHourSelect.appendChild(startOption);

  const endOption = document.createElement("option");
  endOption.value = hour;
  //option.textContent = hour+"시";
  endOption.textContent = hour;
  endHourSelect.appendChild(endOption);
}

const startMinuteSelect = document.getElementById("startMinute");
const endMinuteSelect = document.getElementById("endMinute");

for(let i=0; i<60; i++){
  const minute = String(i).padStart(2, '0');
  const startOption = document.createElement("option");
  startOption.value = minute;
  startOption.textContent = minute;
  startMinuteSelect.appendChild(startOption);

  const endOption = document.createElement("option");
  endOption.value = minute;
  endOption.textContent = minute;
  endMinuteSelect.appendChild(endOption);
}

let submitBtn = document.getElementById('submitBtn');

function validateForm(){
    let thumbnailFile = document.getElementById('thumbnailFile');
    if(!thumbnailFile.files || thumbnailFile.files.length === 0){
        alert("썸네일 이미지를 등록해주세요.");
        return false;
    }
    return true;

}

submitBtn.addEventListener('click',(event)=>{
    if(!validateForm()){
        event.preventDefault();
    }else{
        sendProductList();
    }
})


let dpprice = document.getElementById("dpprice");

function inputNumber(event) {
    const pattern = /[^0-9]/g;

    if (pattern.test(event.target.value)) {
        alert("숫자만 입력해주세요.");
        event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
    }
}

dpprice.addEventListener('keyup', inputNumber);
