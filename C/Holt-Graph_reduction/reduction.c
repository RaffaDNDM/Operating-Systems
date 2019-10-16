/**
* \author Raffaele Di Nardo Di Maio
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "reduction.h"

void printRequests(process *requests, int num_processes, int num_resources);

int main(int argc, char **argv)
{
    char s1[LINE];
    char s2[LINE];

    printf("Write how many resources and processes you're using \n");
    printf("Number of processes \n");
    scanf("%s", s1);
    printf("Number of resources \n");
    scanf("%s", s2);

    int num_processes = atoi(s1);
    int num_resources = atoi(s2);

    process* declarations = malloc(sizeof(process)*(num_processes+1));
    process* allocated = malloc(sizeof(process)*(num_processes+1));
    process* requests = malloc(sizeof(process)*(num_processes+1));

    char *s;

    int i=0;
    for(; i<(num_processes+1); i++)
    {
        declarations[i].id = allocated[i].id = requests[i].id = i;
        declarations[i].resources = malloc(sizeof(int)*num_resources);
        allocated[i].resources = malloc(sizeof(int)*num_resources);
        requests[i].resources = malloc(sizeof(int)*num_resources);

        printf("-------------------------------------------------------------------\n");
        printf("Insert number of allocated resources for process %s \n", procName(i));
        int j=0;
        s = malloc(sizeof(char)*LINE);

        while(j<num_resources)
        {
            printf("R%d:  \n",j+1);
            scanf("%s", s);
            printf("\n");
            int num = atoi(s);

            if(num!=0 || strcmp("0",s)==0)
            {
                if(strcmp("0",s)==0)
                    allocated[i].resources[j++] = 0;
                else
                    allocated[i].resources[j++] = num;
            }

            printf("%d %d\n", j-1, allocated[i].resources[j-1]);
        }

        printf("-------------------------------------------------------------------\n");
        printf("Insert number of requested resources for process %s \n", procName(i));
        j=0;

        printf("----------------\n");
        while(j<num_resources)
        {
            printf("R%d:  \n",j+1);
            scanf("%s", s);
            printf("\n");
            int num = atoi(s);

            if(num!=0 || strcmp("0",s)==0)
            {
                if(strcmp("0",s)==0)
                    requests[i].resources[j++] = 0;
                else
                    requests[i].resources[j++] = num;
            }

            printf("%d %d\n", j-1, requests[i].resources[j-1]);
        }

        printf("-------------------------------------------------------------------\n");

        for(j=0; j<num_resources; j++)
            declarations[i].resources[j] = allocated[i].resources[j]+requests[i].resources[j];
    }

    free(s);

    printTables(declarations, allocated, requests, num_processes, num_resources);

    int result = reduction(declarations, allocated, requests, num_processes, num_resources);
    printf("%s",result?"NO Deadlock":"Deadlock");

    /*
        Fare la free degli array
    */

    return 0;
}

int reduction(process *declarations, process *allocated, process *requests, int num_processes, int num_resources)
{
    int num = 0;
    int i=1;
    int size = num_processes;

    while(num != size && size!=0)
    {
        int x = verifyPossible(requests[i].resources, requests[0].resources, num_resources);

        printf("%d %d %d\n", x, size, i);

        if(verifyPossible(requests[i].resources, requests[0].resources, num_resources))
        {
            reduceTables(&requests, &allocated, i, size, num_resources);
            size--;
            num=0;

            if(size!=0)
                i%=size;
        }
        else
        {
            if(size!=0)
                i = (i+1)%size;
            num++;
        }

        if(i==0)
            i=size;
    }

    if(size==0)
        return true;

    return false;
}

char* procName(int id)
{
    char* name = malloc(sizeof(char)*10);

    if(id==0)
        name = RESOURCE_ID;
    else
    {
        snprintf(name, sizeof(name), "%s%d", PROCESS_ID, id);
    }

    return name;
}

int verifyPossible(int *process_Req, int *available, int num_resources)
{
    int i=0;

    for(; i<num_resources; i++)
    {
        if(process_Req[i]>available[i])
            return false;
    }

    return true;
}

void reduceTables(process **requests, process **allocated, int i, int size, int num_resources)
{
    int j;

    for(j=0; j<num_resources; j++)
    {
        (*requests)[0].resources[j]+=(*allocated)[i].resources[j];
        (*allocated)[0].resources[j]-=(*allocated)[i].resources[j];
    }

    while(i<size)
    {
        int *temp1 = malloc(sizeof(int)*num_resources);
        int *temp2 = malloc(sizeof(int)*num_resources);

        int k=0;
        for(; k<num_resources; k++)
        {
            temp1[k]=(*allocated)[i+1].resources[k];
            temp2[k]=(*requests)[i+1].resources[k];
        }

        (*allocated)[i].id=(*allocated)[i+1].id;
        (*requests)[i].id=(*requests)[i+1].id;
        (*allocated)[i].resources=temp1;
        (*requests)[i].resources=temp2;
        i++;
    }

    free((*requests)[size].resources);
    free((*allocated)[size].resources);

    *requests = realloc(*requests, sizeof(process)*size);
    *allocated = realloc(*allocated, sizeof(process)*size);


    printRequests(*requests, size-1, num_resources);
}

void printTables(process *declarations, process *allocated, process *requests, int num_processes, int num_resources)
{
    int i=0, j = 0;
    printf("Allocated table \n");

    for(; i<(num_processes+1); i++)
    {
        j=0;
        printf("%5s ", procName(i));
        for(; j<num_resources; j++)
        {
            printf("%d  ", allocated[i].resources[j]);
        }
        printf("\n");
    }
    printf("\n");


    i=0;
    printf("Requests table \n");

    for(; i<(num_processes+1); i++)
    {
        j=0;
        printf("%5s ", procName(requests[i].id));
        for(; j<num_resources; j++)
        {
            printf("%d  ", requests[i].resources[j]);
        }
        printf("\n");
    }
    printf("\n");


    i=0;
    printf("Declarations table \n");

    for(; i<(num_processes+1); i++)
    {
        j=0;
        printf("%5s ", procName(i));
        for(; j<num_resources; j++)
        {
            printf("%d  ", declarations[i].resources[j]);
        }
        printf("\n");
    }
    printf("\n");
}

void printRequests(process *requests, int num_processes, int num_resources)
{
    int i=0, j = 0;

    printf("Requests table \n");

    for(; i<(num_processes+1); i++)
    {
        j=0;
        printf("%5s ", procName(requests[i].id));
        for(; j<num_resources; j++)
        {
            printf("%d  ", requests[i].resources[j]);
        }
        printf("\n");
    }

    printf("\n");
}
