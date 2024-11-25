document.getElementById('thumbnailArea').addEventListener('click', function(){
    document.getElementById('thumbnailFile').click();
});

const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$", "i");

//썸네일 이미지 불러오기
function showThumbnailImg(event){
    const thumbnailArea = document.getElementById('thumbnailArea');

    //기존 이미지 비우기
    thumbnailArea.innerHTML = '';

    const file = event.target.files[0];
    
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
//상품을 담을 배열이면서 상품 길이를 반환할 배열
//let productLengthList = [];
let count = 0; //class이름이랑 배열 index를 위한 count

document.getElementById("addList").addEventListener('click', ()=>{

    let name = document.getElementById("dpname").value;
    let price = document.getElementById("dpprice").value;

    if(name === '' || price === ''){
        alert("값을 모두 입력해주세요.");
        return false;
    }

    //console.log("name : "+name+", price : "+price);
    productList.push({dpname : name, dpprice : price});
    //실제 배열의 길이를 반환하기 위한 배열
    //productLengthList.push({dpname : name, dpprice : price});

    //상품 개수 업데이트 함수
    //updateProductCount();

    //@@class이름 만들기
    let dsList = document.createElement("div");
    dsList.classList.add(`dsList${count}`, 'dsListClass');

    //span태그
    //let nameSpan = document.createElement("span");
    //nameSpan.textContent = "상품명: ";

    let nameDiv = document.createElement("div");
    nameDiv.textContent = name;
    nameDiv.classList.add(`dpname${count}`, 'dpnameClass');

    //name div에 span태그 붙이기
    //nameDiv.prepend(nameSpan);
    
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
        //console.log(delBtnClassName);
        let delBtnIndex = delBtnClassName.match(/\d+/);
        //console.log("나는"+delBtnIndex+"번째 버튼입니다~");

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
        //console.log("수정할 index >>> "+editBtnIndex);

        let divName = document.querySelector(`.dpname${editBtnIndex}`);
        let divPrice = document.querySelector(`.dpprice${editBtnIndex}`);
        
        //현재 div에 있는 값 가져오기
        let currentName = divName.textContent;
        let currentPrice = divPrice.textContent;

        if(isEditing){

            //현재 input창에 있는 값 가져오기
            let newName = nameInput.value;
            let newPrice = priceInput.value; 
            //console.log("새롭게 변경된 이름 : "+newName+", 새롭게 변경된 가격 : "+newPrice);

            //nameInput에 있는 값 리스트에 반영하기(제품수정 함수 호출)
            editProduct(editBtnIndex, newName, newPrice);

            //nameInput 없애기
            nameInput.remove();
            priceInput.remove();

            //div에 새로운 값 넣기
            divName.textContent = newName;
            divPrice.textContent = newPrice;
            
            //console.log("수정이 완료되었습니다.");

        }else{
            //console.log("수정하러 들어갑니다");

            // 1. div에 있는 값 없애주기
            divName.textContent = "";
            divPrice.textContent = "";
            //나중에 css하고 div창 display block으로 할지말지 생각해보기..

            //2. input창 만들고 현재입력된 값 넣고나서 div에 이어붙여주기
            nameInput = document.createElement("input");
            nameInput.classList.add(`nameInput${editBtnIndex}`, 'nameInput');
            nameInput.value = currentName;

            priceInput = document.createElement("input");
            priceInput.classList.add(`priceInput${editBtnIndex}`, 'priceInput');
            priceInput.value = currentPrice;

            divName.appendChild(nameInput);
            divPrice.appendChild(priceInput);

            //나중에 checkValidation() 함수를 만들자..
            //다른 리스트랑 name이 중복인지 확인하는거랑, 가격에는 숫자만 들어가도록.
            
        }

        isEditing = !isEditing;

    })

    //index 증가시키기
    count++;

})


//상품 삭제 기능
function deleteProduct(delBtnIndex){
    //console.log("삭제해달라고 온 index : "+delBtnIndex);
    delete productList[delBtnIndex];


}

//상품 수정 기능
function editProduct(editBtnIndex, newName, newPrice){
    //console.log("수정해달라고 온 index : "+editBtnIndex);
    //console.log("원래 리스트에 있는 값 >>> 이름 :  "+productList[editBtnIndex].dpname+" , 가격 : "+
    //    productList[editBtnIndex].dpprice);
    productList[editBtnIndex].dpname = newName;
    productList[editBtnIndex].dpprice = newPrice;
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
        			  //var fileName = files[i].name
        			  //uploadSummernoteImageFile(files[i], this, fileName) fileName이 왜필요하지..?
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
        //console.log(data);
        //console.log(data.url);
        //console.log(data.responseCode);
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


//빈칸이 있으면 제출 막기

let submitBtn = document.getElementById('submitBtn');

function validateForm(){
    let thumbnailFile = document.getElementById('thumbnailFile');
    if(!thumbnailFile.files || thumbnailFile.files.length === 0){
        alert("썸네일 이미지를 등록해주세요.");
        return false;
    }

    const category = document.getElementById("categorySelect");
    if(category.value === ""){
        alert("카테고리를 선택해주세요.");
        return false;
    }

    const startDay = document.getElementById("startDay");
    if(startDay.value.trim() === "" || startDay.value.trim().length === 0){
        alert("시작날짜를 선택해주세요.");
        return false;
    }

    const startHour = document.getElementById("startHour");
    if(startHour.value === ""){
        alert("시작시간을 선택해주세요.");
        return false;
    }

    const startMinute = document.getElementById("startMinute");
    if(startMinute.value === ""){
        alert("시작시간을 선택해주세요.");
        return false;
    }
    
    const endDay = document.getElementById("endDay");
    if(endDay.value.trim() === "" || endDay.value.trim().length === 0){
        alert("종료날짜를 선택해주세요.");
        return false;
    }

    const endHour = document.getElementById("endHour");
    if(endHour.value === ""){
        alert("종료시간을 선택해주세요.");
        return false;
    }

    const endMinute = document.getElementById("endMinute");
    if(endMinute.value === ""){
        alert("종료시간을 선택해주세요.");
        return false;
    }

    const dtitle = document.getElementById("dtitle");
    if(dtitle.value.trim() === "" || dtitle.value.trim().length === 0){
        alert("제목을 입력해주세요.");
        return false;
    }

    const ddetail = document.getElementById("summernote");
    if(ddetail.value.trim() === "" || ddetail.value.trim().length === 0){
        alert("내용을 입력해주세요.");
        return false;
    }

    const productLength = productList.filter(item => item !== undefined).length;

    if(!productList || productList.length === 0 || productLength === 0){
        alert("상품을 한 개 이상 입력해주세요.");
        return false;
    }

    return true;

}

submitBtn.addEventListener('click',(event)=>{
    if(!validateForm()){
        return false;
    }else{
        allData();
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


function allData(){

    //dsvo, date, thumbnail, productList
    
    const formData = new FormData();

    let category = categorySelect.value;

    const dsvo = {
        id : id.value,
        categoryId : category,
        dtitle : dtitle.value,
        ddetail : summernote.value,
    }
    
    //console.log("dsvo >> ", dsvo);
    formData.append("dsvo", new Blob([JSON.stringify(dsvo)], {type : "application/json"}));

    //date

    let dateValue = {
        startDay : startDay.value,
        startHour : startHour.value,
        startMinute : startMinute.value,
        endDay : endDay.value,
        endHour : endHour.value,
        endMinute : endMinute.value
    }
    
    //console.log("dateValue >> ", dateValue);
    formData.append("dateValue", new Blob([JSON.stringify(dateValue)], {type : "application/json"}));
    

    //thumbnailFile
    const thumbnailFile = document.getElementById("thumbnailFile");
    let selectedFile = thumbnailFile.files[0];

    //console.log(selectedFile);
    formData.append("thumbnailFile", selectedFile);

    //productList
    //console.log("productList >> ", productList);
    formData.append("productList", new Blob([JSON.stringify(productList.filter(item=>item!==undefined))],
        {type:"application/json"}));


    // for(let [key,value] of formData.entries()){
    //     console.log(`key : ${key}, Value : `,value);
    // }

    fetch("/demandSurvey/demandRegister", {
        method : "POST",
        body : formData
    })
        .then(response => {
            //console.log("응답상태 코드 : ", response.status);
            return response.json();
        })
        .then(data => {
            //console.log("서버 응답 내용 : ", data);
            if(data.status === "success"){
                window.location.href= "/demandSurvey/list";
            }else{
                alert("글 등록에 실패했습니다.");
            }
        })
        .catch(error => {
            //console.error("에러 발생 :", error);
        })

}
