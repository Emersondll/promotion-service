import yaml
import re
import sys
import os
import argparse
import subprocess

# version 1.0.0

# examples how to execute the script python3 validate-application-yaml.py -m api -b combo-service -v east-global -d external
# required arguments:
#   -mn MN  Microservice folder name
#   -m M    Microservice (relay, consumer, api)
#   -b B    Bees-microservices folder name

# optional arguments:
#   -v V    Change to execute diferents values (east-global, east-us, central-global and central-us)
#   -d D    Change to execute diferents deployments values (external, internal and patch)

parser = argparse.ArgumentParser()
parser._action_groups.pop()
required = parser.add_argument_group('required arguments')
optional = parser.add_argument_group('optional arguments')

required.add_argument('-m', required=True, help="Microservice (relay, consumer, api)")
required.add_argument('-b', required=True, help="Bees-microservices folder name")
optional.add_argument('-v',
                      help="Change to execute diferents values (east-global, east-us, central-global and central-us)",
                      default="value.yaml")
optional.add_argument('-d', help="Change to execute diferents deployments values (external, internal and patch)",
                      default=".")

args = parser.parse_args()

application_file_path = "../../%s/src/main/resources/application.yml" % (args.m)
deployments_file_path = "../../../bees-microservices/charts/%s/templates/" % (args.b)


def build_deployment_var_name(base_var):
    return "- name: %s" % base_var


def build_deployment_vault_var_name(base_var):
    return "export %s=" % base_var


def get_environment_variable(full_string):
    try:
        return re.search('\$\{(.+?):', str(full_string)).group(1)
    except:
        return ''


def verify_environment_variable(application, deployments, errors):
    for actual_node in application:
        if (type(application[actual_node]) == dict):
            verify_environment_variable(application[actual_node], deployments, errors)
        elif (type(application[actual_node]) != list):
            var = get_environment_variable(application[actual_node])
            env_var = build_deployment_var_name(var)
            vault_env_var = build_deployment_vault_var_name(var)
            if (var != '' and (env_var not in deployments) and (vault_env_var not in deployments)):
                errors.append(var)


def get_deployment_template_name():
    deployment_name = "."

    if (args.d in "external"):
        deployment_name = "templates/deployment-external.yaml"
    if (args.d in "internal"):
        deployment_name = "templates/deployment-internal.yaml"
    if (args.d in "patch"):
        deployment_name = "templates/deployment-patch.yaml"

    return deployment_name


def get_template_values_file_name():
    values_file = "values.yaml"

    if (args.v in "east-global"):
        values_file = "values-eastus2.yaml"

    if (args.v in "east-us"):
        values_file = "values-eastus2-us.yaml"

    if (args.v in "central-global"):
        values_file = "values-centralus.yaml"

    if (args.v in "central-us"):
        values_file = "values-centralus-us.yaml"

    return values_file


def execute_helm_template():
    #  helm template templates/deployment-external.yaml -f values.yaml
    print("\033[1;97m", "helm template started")

    bees_ms_path = "../../../bees-microservices/charts/%s/" % (args.b)

    values_file = get_template_values_file_name()
    deployment_name = get_deployment_template_name()

    command = "helm template %s -f %s" % (deployment_name, values_file)

    print(command)
    subprocess.call(command, cwd=bees_ms_path, shell=True)


with open(application_file_path, "r") as application_file_stream:
    try:
        application = yaml.load(application_file_stream)
    except Exception as ex:
        print("##[error] Not able to parse application.yml")
        print(ex)
        sys.exit(1)

    found_errors = False
    list_deployments = [f for f in os.listdir(deployments_file_path) if "deployment" in f]
    for f in list_deployments:
        f_deployment = "%s%s" % (deployments_file_path, f)
        with open(f_deployment, "r") as deployments_file_stream:
            errors = []
            deployments = deployments_file_stream.read().replace('\n', '')
            verify_environment_variable(application, deployments, errors)

            if (errors):
                errors = str(errors).strip('[]')
                print("\033[91m", "##[error]  not able to find - %s" % errors)
                found_errors = True
            else:
                print("\033[92m", "Everything is looking fine!")

        deployments_file_stream.close()

    if found_errors:
        sys.exit(1)

    execute_helm_template()
    sys.exit(0)

