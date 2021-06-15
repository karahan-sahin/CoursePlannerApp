from bs4 import BeautifulSoup
import requests
import re
import json
import unicodedata
from collections import defaultdict

# # Send request in (Kotlin) for downloading the course syllabus pdf --> Which will be used for 
# ## https://registration.boun.edu.tr/scripts/schedule/coursedescription.asp?course=CL%20%20488&section=01&term=2020/2021-3


# Fix firebase standards --> cannot containg [$ # [ ] / or .] at the keys
  
def extract_courses(url="2020-2021-2.html"):

    html = open("2020-2021-2.html", "r").read()

    urls = {child.text.strip(" \n") : child["href"] for child in BeautifulSoup(html, 'html.parser').select('td > a')}

    COURSES = defaultdict(int)

    for program, url in urls.items():

        context = BeautifulSoup(requests.get(url).content.decode("iso-8859-9",'ignore'), 'html.parser')

        courses = context.find_all("tr", {"class": ["schtd2", "schtd"]})

        hold = ""
        for course in courses:
                
            info =  course.select("td")

            try:
                # This part attempts to find syllabus within description url
                desc_url = "https://registration.boun.edu.tr" + re.findall(r'\"(/scripts/schedule/.+?)\"',info[1].findChild("a")["onclick"])[0].replace("§","&sect")
                status = BeautifulSoup(requests.get(desc_url).text, 'html.parser').find_all("td",{"colspan":"3","align":"center"})[4]

                if status.text.strip(" \n") == "No Syllabus File found This Course":
                    status = "None"

                else:
                    status = "https://registration.boun.edu.tr" + status.a["href"]

                course_name = hold = unicodedata.normalize("NFKD", info[0].text).strip(" \n")

                COURSES[course_name.replace(".",",")] = {

                    "syllabus": status,
                    "course_name" : unicodedata.normalize("NFKD", info[2].text).strip(" "),
                    "credit" : unicodedata.normalize("NFKD", info[3].text).strip(" "),
                    "ects" : unicodedata.normalize("NFKD", info[4].text).strip(" "),
                    "instructor" : unicodedata.normalize("NFKD", info[5].text).strip(" "),

                    # This is a module problem --> write in kotlin
                    "days" : unicodedata.normalize("NFKD", info[6].text).strip(" "), # write a parser module for the
                    "hours" : unicodedata.normalize("NFKD", info[7].text).strip(" ") # write a parser module for these
                
                }

            # Hold for PS and Labs
            except:
                for i in ["I","II","III","IV","V"]:
                    
                    # Create lab name
                    lab_name = f"{hold} {unicodedata.normalize('NFKD', info[2].text).strip(' ')}"

                    # Extract Lab Information
                    if type(COURSES[lab_name]) == int:
                        COURSES[lab_name] = {

                            "syllabus": "_",
                            "course_name" : lab_name,
                            "credit" : "_",
                            "ects" : "_",
                            "instructor" : unicodedata.normalize("NFKD", info[5].text).strip(" "),

                            # This is a module problem --> write in kotlin
                            "days" : unicodedata.normalize("NFKD", info[6].text).strip(" "), # write a parser module for the
                            "hours" : unicodedata.normalize("NFKD", info[7].text).strip(" ") # write a parser module for these

                        }
                        break

                    else:
                        pass

        with open('courses.json', 'w', encoding="iso-8859-9") as f_out:
            json.dump(COURSES, f_out, indent=2, ensure_ascii=True)

def unicode_fix(filename='courses.json'):

    unicode_map = {"G\\u0306":"Ğ",
                   "C\\u0327":"Ç",
                   "O\\u0308":"Ö",
                   "U\\u0308":"Ü", 
                   "I\\u0307":"İ"}

    with open('courses.json', 'r', encoding="iso-8859-9") as f_in:
        updated = defaultdict()
        course = json.loads(f_in.read())
        for c in course.keys():
            updated[c.replace(".","-")] = course[c]

    with open('courses1.json', 'w', encoding="iso-8859-9") as f_out:
        json.dump(updated, f_out, indent=2, ensure_ascii=True)


with open('courses1.json', 'r', encoding="utf-8") as f_in:
    course = json.loads(f_in.read())
    updated = course
    for c in course["Courses"].keys():
        print(c)
        if "10" in updated["Courses"][c]["hour"]:
            print(updated["Courses"][c])


# with open('courses1.json', 'w', encoding="utf-8") as f_out:
#     json.dump(updated, f_out, indent=2, ensure_ascii=True)