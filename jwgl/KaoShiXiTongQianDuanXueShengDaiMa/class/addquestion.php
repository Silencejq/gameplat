<?php 
$servername = "localhost";
$username = "root";
$password = "123";
$dbname = "guanli";
 // 创建连接
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
$a=isset($_POST['type'])?$_POST['type']:0;

if($a==1){
    $question=$_POST['question'];
    $answer=$_POST['answer'];
    $sql='insert into think_question1("question1","answer") values ("'.$question.'","'.$answer.'");';
    $result=mysqli_query($conn,$sql);
    if($result){echo '成功';}
}

if($a==2){
    $question=$_POST['question'];
    $answer1=$_POST['answer1'];
    $answer2=$_POST['answer2'];
    $answer3=$_POST['answer3'];
    $answer4=$_POST['answer4'];
    $sql='insert into think_question2("question2","isa","isb","isc","isd") values 
    ("'.$question.'","'.$answer1.'","'.$answer2.'","'.$answer3.'","'.$answer4.'")';
    $result=mysqli_query($conn,$sql);
    if($result){echo '成功';}
}

if($a==3){
    $question=$_POST['question'];
    $answer=$_POST['answer'];
    $sql='insert into think_question3("question1","answer") values ("'.$question.'","'.$answer.'");';
    $result=mysqli_query($conn,$sql);
    if($result){echo '成功';}
}

if($a==4){
    $question=$_POST['question'];
    $answer=$_POST['answer'];
    $sql='insert into think_question4("question1","answer") values ("'.$question.'","'.$answer.'");';
    $result=mysqli_query($conn,$sql);
    if($result){echo '成功';}
}