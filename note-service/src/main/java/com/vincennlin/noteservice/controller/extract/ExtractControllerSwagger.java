package com.vincennlin.noteservice.controller.extract;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "[NEW] Extract Controller",
        description = "從檔案提取文字相關的 API ，前端不會直接用到"
)
public interface ExtractControllerSwagger {

    @Operation(
            summary = "從檔案中提取文字",
            description = "從檔案中提取文字。目前支援 .pdf .txt .docx 三種格式。必須指定 mediaType 為 multipart/form-data。 " +
                    "Request body 必須是檔案，其中 key 為 'file'，value 為該檔案"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功提取文字",
            content = @Content(
                    mediaType = "application/text",
                    examples = @ExampleObject(value = """
                            package com.vincennlin.misc.findmiddlenode;
                                                        
                            public class LinkedList {
                                                        
                                private Node head;
                                private Node tail;
                                                        
                                class Node {
                                    int value;
                                    Node next;
                                                        
                                    Node(int value) {
                                        this.value = value;
                                    }
                                }
                                                        
                                public LinkedList(int value) {
                                    Node newNode = new Node(value);
                                    head = newNode;
                                    tail = newNode;
                                }
                                                        
                                public Node getHead() {
                                    return head;
                                }
                                                        
                                public Node getTail() {
                                    return tail;
                                }
                                                        
                                public void printList() {
                                    Node temp = head;
                                    while (temp != null) {
                                        System.out.println(temp.value);
                                        temp = temp.next;
                                    }
                                }
                                                        
                                public void printAll() {
                                    if (head == null) {
                                        System.out.println("Head: null");
                                        System.out.println("Tail: null");
                                    } else {
                                        System.out.println("Head: " + head.value);
                                        System.out.println("Tail: " + tail.value);
                                    }
                                    System.out.println("\\nLinked List:");
                                    if (head == null) {
                                        System.out.println("empty");
                                    } else {
                                        printList();
                                    }
                                }
                                                        
                                public void makeEmpty() {
                                    head = null;
                                    tail = null;
                                }
                                                        
                                public void append(int value) {
                                    Node newNode = new Node(value);
                                    if (head == null) {
                                        head = newNode;
                                        tail = newNode;
                                    } else {
                                        tail.next = newNode;
                                        tail = newNode;
                                    }
                                }
                               \s
                                public Node findMiddleNode() {
                                    Node slow = head;
                                    Node fast = head;
                                    while (fast != null && fast.next != null) {
                                        slow = slow.next;
                                        fast = fast.next.next;
                                    }
                                    return slow;
                                }
                            }
                                                        
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> extractTextFromFile(@RequestPart("file")
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                    description = "檔案",
                                                    required = true,
                                                    content = @Content(
                                                            mediaType = "multipart/form-data",
                                                            examples = @ExampleObject(value = """
                                                                    {
                                                                        "file": "檔案"
                                                                    }
                                                                    """)
                                                    )
                                              ) MultipartFile file);


//    @Operation(
//            summary = "從圖片中提取文字",
//            description = "從圖片中提取文字。{language} 可以輸入 eng 或 chi 。" +
//                    "必須指定 mediaType 為 multipart/form-data。 " +
//                    "Request body 必須是圖片檔案，其中 key 為 'file'，value 為該圖片檔案"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "成功提取文字",
//            content = @Content(
//                    mediaType = "application/text",
//                    examples = @ExampleObject(value = """
//                            eazy
//                            JWT TOKENS A
//                            v JWT means JSON Web Token. It is a token implementation which will be in the JSON format and designed to
//                            use for the web requests.
//                            v" JWT is the most common and favorite token type that many systems use these days due to its special features
//                            and advantages.
//                            v JWT tokens can be used both in the scenarios of Authorization/Authentication along with Information
//                            exchange which means you can share certain user related data in the token itself which will reduce the burden
//                            of maintaining such details in the sessions on the server side.
//                            A JWT token has 3 parts each separated by a period(.) Below is a sample JWT token,
//                            RO DESNNIENIDIFEG . S (1 KxwRISMeKKF2QT4fwpMeJf36POk6yJV_adQsswsc
//                            . -
//                            8. Signature (Optional)
//                            """)
//            )
//
//    )
//    @SecurityRequirement(name = "Bear Authentication")
//    ResponseEntity<String> extractTextFromImage(@PathVariable(value = "language") ExtractLanguage language,
//                                                @RequestPart("file") MultipartFile file);
}
