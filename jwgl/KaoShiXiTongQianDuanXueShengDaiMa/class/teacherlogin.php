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
$pname = isset($_POST["username"]) ? $_POST["username"] : "";
$ppwd = isset($_POST["passwd"]) ? $_POST["passwd"] : "";
if($pname==""){
    echo '干啥呢老弟？';
    exit;
}


$p = array("message"=>"fault");
if($pname==0 OR $ppwd==0){$q= json_encode($p);echo $q;exit;}
$sql='select * from think_user where user_name ="'.$pname.'"';
$result = mysqli_query($conn,$sql);
if($result[1]!=$ppwd){$q= json_encode($p);echo $q;exit;}
$p[0]=="Success";
$p["sid"]=$pname;


$sql='select * from think_teacher where teacher_id ="'.$result[7].'"';
$result2=mysqli_query($conn,$sql);
$p["name"]=$result2[1];
$q= json_encode($p);
echo $q;
?>