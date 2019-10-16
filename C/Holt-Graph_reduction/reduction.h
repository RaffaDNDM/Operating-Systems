/**
* \author Raffaele Di Nardo Di Maio
*/

#ifndef REDUCTION
#define REDUCTION

#define LINE 20
#define true 1
#define false 0
#define RESOURCE_ID "S"
#define PROCESS_ID "P"

struct process_entry
{
    //id=0 for the resources, id!=0 for the processes
    int id;
    //array of resources requests for each process
    int *resources;
};

typedef struct process_entry process;

int reduction(process *declarations, process *allocated, process *requests, int num_processes, int num_resources);
char* procName(int id);
int verifyPossible(int *process_Req, int *available, int num_resources);
void reduceTables(process **requests, process **allocated, int i, int size, int num_resources);
void printTables(process *declarations, process *allocated, process *requests, int num_processes, int num_resources);

#endif
