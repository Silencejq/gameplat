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
$id=isset($_POST["id"])?$_POST["id"]:'';
if($id==''){echo "??";exit;}
$p[0]=$_POST["name"];$p[1]=$_POST["class"];$p[2]=$_POST["birthday"];$p[3]=$_POST["home"];

$sql="update think_student set student_name='".$p[0]."',".
"class='".$p[1]."',".
"student_birthday='".$p[2]."',".
"student_home=".$p[3]."'where student_id=".$id;
$result=mysqli_query($conn,$sql);
if($result){
$pp=array('message'=>'Success');
$q=json_encode($pp);
echo $q;
}
?>