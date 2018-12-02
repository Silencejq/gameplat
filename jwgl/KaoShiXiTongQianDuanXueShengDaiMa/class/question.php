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
$q1=isset($_POST['q1'])?$_POST['q1']:0;
$q2=isset($_POST['q2'])?$_POST['q2']:0;
$q3=isset($_POST['q3'])?$_POST['q3']:0;
$q4=isset($_POST['q4'])?$_POST['q4']:0;
if($q1){
    $sql='select count(*) from think_question1';
    $result=mysqli_query($conn,$sql);
    $num= range(1,$result);
    shuffle($num);
    $question1=array();
    for($i=0;$i<$q1;$i++){
        $sql='select * from think_question1 where question1id="'.$num[$i].'"';
        $question1[$i]=mysqli_query('$conn','$sql');
    }
    $p1=json_decode($question1);
    echo $p1;
}


if($q2){
    $sql='select count(*) from think_question2';
    $result=mysqli_query($conn,$sql);
    $num= range(1,$result);
    shuffle($num);
    $question2=array();
    for($i=0;$i<$q2;$i++){
        $sql='select * from think_question2 where question2id="'.$num[$i].'"';
        $question2[$i]=mysqli_query('$conn','$sql');
    }
    $p2=json_decode($question2);
    echo $p2;
}


if($q3){
    $sql='select count(*) from think_question3';
    $result=mysqli_query($conn,$sql);
    $num= range(1,$result);
    shuffle($num);
    $question3=array();
    for($i=0;$i<$q3;$i++){
        $sql='select * from think_question3 where question3id="'.$num[$i].'"';
        $question3[$i]=mysqli_query('$conn','$sql');
    }
    $p3=json_decode($question3);
    echo $p3;
}


if($q4){
    $sql='select count(*) from think_question4';
    $result=mysqli_query($conn,$sql);
    $num= range(1,$result);
    shuffle($num);
    $question4=array();
    for($i=0;$i<$q4;$i++){
        $sql='select * from think_question4 where question4id="'.$num[$i].'"';
        $question4[$i]=mysqli_query('$conn','$sql');
    }
    $p4=json_decode($question4);
    echo $p4;
}
?>