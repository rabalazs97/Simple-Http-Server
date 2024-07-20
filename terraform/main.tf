terraform {
  required_providers {
    kubernetes = {
      source = "hashicorp/kubernetes"
      version = "2.31.0"
    }
  }
}

provider "kubernetes" {
    config_path    = "~/.kube/config"
    config_context = "minikube"
}

resource "kubernetes_manifest" "namespace" {
  manifest = yamldecode(file("./namespace.yaml"))
}

resource "kubernetes_manifest" "deployment" {
  manifest = yamldecode(file("./deployment.yaml"))
}

resource "kubernetes_manifest" "service" {
  manifest = yamldecode(file("./service.yaml"))
}
