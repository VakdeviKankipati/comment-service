provider "aws" {
  region                      = "us-east-1"
  access_key                  = "test"  
  secret_key                  = "test"
  s3_use_path_style           = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    s3  = "http://localhost:4566"
    ec2 = "http://localhost:4566"
  }
}

resource "aws_s3_bucket" "comment_service_bucket" {
  bucket = "comment-service-bucket"
  tags = {
    Name        = "comment-service-bucket"
    Environment = "local"
  }
}


resource "aws_instance" "comment_service_instance" {
  ami           = "ami-12345678"   # Dummy AMI ID (LocalStack accepts any string)
  instance_type = "t2.micro"

  tags = {
    Name        = "comment-service-ec2"
    Environment = "local"
  }
}
