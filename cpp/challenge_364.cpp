//  https://www.reddit.com/r/dailyprogrammer/comments/8s0cy1/20180618_challenge_364_easy_create_a_dice_roller/
//  challenge_364.cpp
//  
//
//  Created by Devin on 7/2/18.
//

#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>
#include <sstream>
#include <utility>

/**
 Returns a random number between 1 and the input parameter.
 
 @param upper_bound
 @return
 */
int get_random_for_die(int upper_bound) {
    return rand() % upper_bound + 1;
}

/**
 Return a parsed pair from the input: where the first pair is the number
 of die to be rolled and the second is the number of sides of the die.
 
 @param input to be parsed from the command line
 @return
 */
std::pair<int, int> split_input_die_string(const std::string& input) {
    
    std::istringstream ss(input);
    std::string token;
    int count, sides;
    
    std::getline(ss, token, 'd');
    count = stoi(token);
    std::getline(ss, token, 'd');
    sides = stoi(token);

    return std::make_pair(count, sides);
}

int main() {
    
    //initialize
    srand (time(NULL));
   
    std::string input;

    while(true) {
        //read from input
        std::cin >> input;
        //check if we're done
        if(input.find("q") != std::string::npos) {
            break; //exit
        }
        
        std::pair<int,int> die_pair = split_input_die_string(input);

        int die_sum;
        std::string rolls;
        
        for(int i=0; i<die_pair.first; i++) {
            int roll_result = get_random_for_die(die_pair.second);
            rolls += " " + std::to_string(roll_result);
            die_sum += roll_result;
        }

        std::cout << die_sum << ':' << rolls << '\n';
    }
}
