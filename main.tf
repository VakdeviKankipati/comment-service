provider "aws" {
  region                      = "us-east-1"
  access_key                  = "LKIAQAAAAAAABOIDCFQB"
  secret_key                  = "pGUf6rbHmhalj993EZVwtSaYDQefQCIG/M9r1hOj"
  s3_use_path_style           = true
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    s3 = "http://localhost:4566"
  }
}


resource "aws_s3_bucket" "bucket" {
  bucket = "localstack-bucket-1"
}
