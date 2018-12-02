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
$pid=isset($_GET["id"])?$_GET["id"]:"";
if($pid==''){echo "你怎么回事？";exit;}
$p=array();
$p["message"]='fault';
$sql="select * from think_student where student_id =".$pid;
$result=mysqli_query($conn,$sql);
$p['message']="Success";$p['name']=$result[1];
$p['class']=$result[2];$p['birthday']=$result[3];$p['home']=$result[4];
$q=json_encode("$p");
echo $q;
?>