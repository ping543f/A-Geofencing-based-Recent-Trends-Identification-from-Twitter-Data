#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Aug  3 09:55:35 2019

@author: tourist800/Gollam Rabby
"""

import csv
import re
import nltk
import string
import itertools
from nltk.tokenize import word_tokenize
from porter2stemmer import Porter2Stemmer
from nltk.stem import WordNetLemmatizer 
from nltk.corpus import stopwords
from nltk.corpus import wordnet

ps = Porter2Stemmer()
lemmatizer = WordNetLemmatizer()
stop_words = set(stopwords.words('english'))
        
class TweetNode:
    def __init__(self,tweet,phrase,word,synonymslist):
        
        self.Tweet = tweet
        self.phrase = phrase
        self.word = word
        self.synonymslist = synonymslist
        self.WordCountEachTweet= 0
        self.totalfound = 0
        self.totalhashfound = 0
        self.score = 0
        
class HashtagNode:
    def __init__(self,hashtag,count):
        self.Hashtag = hashtag
        self.Count = count

class DataProcessing:
    def clean_tweet(self,tweet): 
            ''' 
            Utility function to clean tweet text by removing links, special characters 
            using simple regex statements. 
            '''
            return ' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t]) |(\w+:\/\/\S+)", " ", tweet).split()) 


        


    def synonyms(self,word):
        synonyms = []
        for syn in wordnet.synsets(word):
            for l in syn.lemmas():
                if l.name() not in synonyms:
                    synonyms.append(l.name())
        return synonyms
        
    def importent_tweet(self,tweet,candidate_phrases,TweetNode_list):
        for i in range(0,len(candidate_phrases),1):
          
            Split_candidate_phrases = candidate_phrases[i].split(" ")
            for j in range(0,len(Split_candidate_phrases),1):
                
                TweetNode_list.append(TweetNode(tweet,candidate_phrases[i],Split_candidate_phrases[j],self.synonyms(Split_candidate_phrases[j])))
           
        return TweetNode_list
      
    
    def final_ranking(self,TweetNode_list,Hashtag_node_list):
        Hashtag_word = []
        for word in range(0,len(Hashtag_node_list),1):
            Hashtag_word.append(Hashtag_node_list[word].Hashtag)
        
        for i in range(0,len(TweetNode_list),1):
            if TweetNode_list[i].word in Hashtag_word:
                hashtag_node_index = Hashtag_word.index(TweetNode_list[i].word)
                TweetNode_list[i].totalhashfound = Hashtag_node_list[hashtag_node_index].Count
                
                if TweetNode_list[i].totalhashfound != 0:
                    TweetNode_list[i].score = TweetNode_list[i].totalfound * 2
                   
            else:
                TweetNode_list[i].score = TweetNode_list[i].totalfound
                    
                
                
        
        return TweetNode_list
    
    def importent_word_count(self,TweetNode_list):
        word_list = []
        tweet_word_list = []
        
        for each_tweet_node in range(0,len(TweetNode_list),1):
            tweet_split = TweetNode_list[each_tweet_node].Tweet.replace('#','').split( )
            for tweet_split_word in range(0,len(tweet_split),1):
                tweet_word_list.append(lemmatizer.lemmatize(tweet_split[tweet_split_word]))
                
            TweetNode_list[each_tweet_node].WordCountEachTweet = tweet_word_list.count(TweetNode_list[each_tweet_node].word)
            tweet_word_list.clear()
            
        for i in range(0,len(TweetNode_list),1):
            word_list.append(TweetNode_list[i].word)
            
        for i in range(0,len(TweetNode_list),1):
         
            TweetNode_list[i].totalfound  = word_list.count(TweetNode_list[i].word)
            
                
        return TweetNode_list
    

    def hashtag_count(self,Hashtag_list,Hashtag_node_list):
        for i in range(0,len(Hashtag_list),1):
            count = Hashtag_list.count(Hashtag_list[i])
            Hashtag_node_list.append(HashtagNode(Hashtag_list[i],count))
            
        return Hashtag_node_list
            
        
    
    def find_hashtag(self,Hashtag_list,tweet):
        remove_string = ['_','@','-','#','…','??','.','http']
        tweet_split = self.clean_tweet(tweet).split(" ")
        for i in range(0,len(tweet_split),1):
            if '#' in tweet_split[i]:
                removehashsplit = tweet_split[i].split()
                if '#' in removehashsplit:
                    removehashsplit.remove('#')
                withouthash = ''.join(removehashsplit)
                    
                if ',' not in withouthash:
                    for check_remove_string in range(0, len(remove_string),1):
                        if remove_string[check_remove_string] in withouthash:
                            Hashtag_list.append(lemmatizer.lemmatize(withouthash.replace(remove_string[check_remove_string], " ").strip()))
                            synonyms = self.synonyms(lemmatizer.lemmatize(withouthash.replace(remove_string[check_remove_string], " ").strip()))
                            for each_synonyms in range(0,len(synonyms),1):
                                if synonyms[each_synonyms].replace("_", " ").lower() not in Hashtag_list:
                                    Hashtag_list.append(synonyms[each_synonyms].replace("_", " ").lower())
                if ',' in withouthash:
                    split_coma = withouthash.split(",")
                    for j in range(0,len(split_coma),1):
                        for check_remove_string in range(0, len(remove_string),1):
                            Hashtag_list.append(lemmatizer.lemmatize(split_coma[j].replace(remove_string[check_remove_string], " ").strip()))
                            synonyms = self.synonyms(lemmatizer.lemmatize(split_coma[j].replace(remove_string[check_remove_string], " ").strip()))
                            for each_synonyms in range(0,len(synonyms),1):
                                if synonyms[each_synonyms].replace("_", " ").lower() not in Hashtag_list:
                                    Hashtag_list.append(synonyms[each_synonyms].replace("_", " ").lower())
                      
                
        return Hashtag_list

    
    def Sorting_Tweet(self,TweetNode_list ):
        for i in range(0,len(TweetNode_list),1):
            for j in range(len(TweetNode_list)-1-i):
                if TweetNode_list[j].score < TweetNode_list[j+1].score:
                    TweetNode_list[j], TweetNode_list[j+1] = TweetNode_list[j+1], TweetNode_list[j]
    
        return TweetNode_list
    
    def writeTweetNode(self,f,TweetNode_list):
        
        xyz = []
        abc = []
        
        for i in range(0,len(TweetNode_list),1):
            
            if TweetNode_list[i].word not in abc:
                if len(TweetNode_list[i].word)>1:
                
                    abc.append(str(TweetNode_list[i].word))
                   
                    xyz.append(str(TweetNode_list[i].word)+", "+str(TweetNode_list[i].totalfound)+", "+str(TweetNode_list[i].totalhashfound)+", "+str(TweetNode_list[i].score))
                   
            
                    f.write("Tweet: "+str(TweetNode_list[i].Tweet)+"\n")
                    f.write("Candidate phrases: "+str(TweetNode_list[i].phrase)+"\n")
                    f.write("Candidate phrases word: "+str(TweetNode_list[i].word)+"\n")
                    f.write("Synonyms of phrases word: "+str(TweetNode_list[i].synonymslist)+"\n")
                    f.write("Candidate phrases Word Count EachTweet: "+str(TweetNode_list[i].WordCountEachTweet)+"\n")
                    f.write("Candidate phrases word count total: "+str(TweetNode_list[i].totalfound)+"\n")
                    f.write("total hash found: "+str(TweetNode_list[i].totalhashfound)+"\n")
                    f.write("Candidate phrases score: "+str(TweetNode_list[i].score)+"\n\n")
         

    def writeHashtag(self,f,Hashtag_node_list):
        for i in range(0,len(Hashtag_node_list),1):
            f.write("Hashtag: "+str(Hashtag_node_list[i].Hashtag)+"\n")
            f.write("Hashtag count: "+str(Hashtag_node_list[i].Count)+"\n")
            
        f.write("\n\n")
           
    

class ExtractCandidate:  
    
    def __init__(self, text, select = 1): 
        self.text = text
        
        if select == 1:
            self.extract_candidate_chunks()
        
    def extract_candidate_chunks(self):
        grammar=r'KT: { (<NN.*>+ <JJ.*>?)|(<JJ.*>? <NN.*>+)}'
        punct = set(string.punctuation)
        stop_words = set(nltk.corpus.stopwords.words('english'))
        chunker = nltk.chunk.regexp.RegexpParser(grammar)
        tagged_sents = nltk.pos_tag_sents(nltk.word_tokenize(sent) for sent in nltk.sent_tokenize(self.text))
        all_chunks = list(itertools.chain.from_iterable(nltk.chunk.tree2conlltags(chunker.parse(tagged_sent)) for tagged_sent in tagged_sents))
        candidates = [' '.join(word for word, pos, chunk in group).lower() for key, group in itertools.groupby(all_chunks, lambda word__pos__chunk: word__pos__chunk[2] != 'O') if key]
        x = [cand for cand in candidates if cand not in stop_words and not all(char in punct for char in cand)]
        
        
        
        data= []
        
        for i in range(0,len(x),1):
            if len(x[i].split())==1:
                if re.match("^[A-Za-z0-9]*$", x[i]):
                    if len(x[i])>2:
                        data.append(x[i])
                    
            else:
                add=""
                split = x[i].split()
                lenth = len(split)
                for i in range(0,lenth,1):
                    king = re.match("^[A-Za-z0-9]*$", split[i])
                    if len(str(king))>2:
                        add =add +" "+split[i]
                data.append(add.strip())
        
        return data
        
 
    def CleaningCandidatePhrases (self, probable_phrases):
        probpr = []
        
        for i in range (0, len(probable_phrases), 1):
            words = word_tokenize(probable_phrases[i]) 
            wa = []
            for w in words:
                x = lemmatizer.lemmatize(w)
                wa.append(x)
            probable_phrases[i] = (' '. join(wa))
            

        
        for i in range (0, len(probable_phrases), 1):
            for j in range(0,len(probable_phrases[i]),1):
                regex = re.compile('[@_!#$%^&*()<>?/\|}{~:].')
                if regex.search(probable_phrases[i]) == None:
                    probpr.append(probable_phrases[i])
                    break
                else:
                    continue
                    
        for i in range (0, len(probable_phrases), 1):
            temp_probable_phrases = probable_phrases[i].split(" ")
            min_length = True
            for k in range(0,len(temp_probable_phrases),1):
                if len(temp_probable_phrases[k]) < 2:
                    min_length = False
            
            
            if min_length:
                count = 0
                for j in range (0, len(probable_phrases), 1):
                    if probable_phrases[i] == probable_phrases[j]:
                        count = count + 1
            
                    if count > 2:
                        probpr.append(probable_phrases[i]) 
                        break  
            else:
                continue
                    
        return probpr    
    
    def CandidatePhraseHandler (self):
        candidate_phrases = self.extract_candidate_chunks()
        candidate_phrases = self.CleaningCandidatePhrases (candidate_phrases)
        return candidate_phrases




     
def main():
    
    DP = DataProcessing()
    
    
    
    f = open("tweet_save.txt", "w")
 
    TweetNode_list = []
    Hashtag_list = []
    Hashtag_node_list = []
    
    with open('data.csv',encoding='latin-1') as csvDataFile:
        csvReader = csv.reader(csvDataFile)
        for row in csvReader:
           
            DP.find_hashtag(Hashtag_list,row[12].lower())
            extract_candidate = ExtractCandidate(row[12].lower())
            candidate_phrases = extract_candidate.CandidatePhraseHandler()
            if candidate_phrases != []:
               
                DP.importent_tweet(row[12].lower(),candidate_phrases,TweetNode_list)
    
    a = DP.importent_word_count(TweetNode_list)
    b = DP.hashtag_count(Hashtag_list,Hashtag_node_list)
    c = DP.final_ranking(a,b)
    d = DP.Sorting_Tweet(c)
    
    DP.writeTweetNode(f,d)
    

if __name__ == "__main__":
    main()
