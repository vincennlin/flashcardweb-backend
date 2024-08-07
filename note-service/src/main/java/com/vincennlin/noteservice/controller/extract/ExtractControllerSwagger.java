package com.vincennlin.noteservice.controller.extract;

import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "[NEW] Extract Controller",
        description = "從檔案提取文字相關的 API ，前端不會直接用到"
)
public interface ExtractControllerSwagger {

    @Operation(
            summary = "從 PDF 檔案中提取文字",
            description = "從 PDF 檔案中提取文字。必須指定 mediaType 為 multipart/form-data。 " +
                    "Request body 必須是 PDF 檔案，其中 key 為 'file'，value 為該 PDF 檔案"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功提取文字",
            content = @Content(
                    mediaType = "application/text",
                    examples = @ExampleObject(value = """
                            High Speed Network Lab
                            Recurrent Neural Networks
                            (RNN)
                            High Speed Network Lab 2
                            Machine Learning
                            Outline\s
                            vIntroduction
                            vRNN
                            vTraining RNN
                            vDeep RNN
                            vForecasting Several Time Steps Ahead
                            vHandling Long Sequences
                            ØLong Short-Term Memory (LSTM)
                            ØThe Gated Recurrent Unit (GRU)
                            High Speed Network Lab 3
                            Machine Learning
                            Introduction
                            vPredict next value in a sequence
                            vRNN: recurrent neural network
                            Ø Take input sequences of arbitrary length
                            Ø Perform back propagation through time
                            Ø Difficulties: unstable gradients, very limited short-term memory
                            vOther nets can handle sequences:
                            Ø Deep net: short sequences
                            Ø CNNs: long sequences
                            High Speed Network Lab 4
                            Machine Learning
                            Introduction
                            vPredict next value in a sequence
                            vRNN: recurrent neural network
                            Ø Take input sequences of arbitrary length
                            Ø Perform back propagation through time
                            Ø Difficulties: unstable gradients, very limited short-term memory
                            vOther nets can handle sequences:
                            Ø Deep net: short sequences
                            Ø CNNs: long sequences
                            High Speed Network Lab 5
                            Machine Learning
                            Applications of RNN
                            vImage Captioning
                            ØRNNs are used to caption an image by analyzing the activities present.
                            vTime Series Prediction
                            ØAny time series problem, like predicting the prices of stocks in a particular\s
                            month, can be solved using an RNN
                            vNatural Language Processing
                            ØText mining and Sentiment analysis can be carried out using an RNN for\s
                            Natural Language Processing (NLP)
                            vMachine Translation
                            ØGiven an input in one language, RNNs can be used to translate the input into\s
                            different languages as output
                            High Speed Network Lab 6
                            Machine Learning
                            Recurrent neurons (RN) and layers(1/3)
                            v Recurrent neurons and layers
                            Ø Activations flow from in to out (feedforward)
                            Ø RNN includes feedback: neuron takes sum of input and its previous output
                            Ø Package into layers
                            High Speed Network Lab 7
                            Machine Learning
                            Recurrent neurons and layers(2/3)
                            Ø Each neuron in the layer gets all inputs and all outputs
                            Ø Each neuron has a 2 sets of weights, one for inputs and one for the\s
                            previous outputs
                            Ø Considering the layer we can express the weights as matrices Wx\s
                            and Wy giving the output vector as
                            üactivation function ϕ and bias b.\s
                            High Speed Network Lab 8
                            Machine Learning
                            Recurrent neurons and layers(3/3)
                            High Speed Network Lab 9
                            Machine Learning
                            Memory Cells(1/2)
                            v RN output depends on all previous inputs: memory
                            v Single layer can only learn short patterns (e.g. 10 steps)
                            v Can generalize the RN feedback as the feedback of a hidden state
                            Ø h(t) that is not necessarily the RN output\s
                            v The hidden state represents previous inputs
                            High Speed Network Lab 10
                            Machine Learning
                            Memory Cells(2/2)
                            High Speed Network Lab 11
                            Machine Learning
                            Input and Output Sequences(1/7)
                            v IO sequences
                            Ø An RNN can simultaneously take a sequence of inputs and produce a\s
                            sequence of outputs
                            üSequence to sequence: take a sequence as input and output a sequence\s
                            ü (e.g. shifted one time step forward to predict next value)
                            Ø Why bother with this when only the last output contains new info?
                            üBecause the net may be trained to do more than just a project one step forward in time,\s
                            may involve other transformations
                            ØTypes:
                            üVector-to-Vector (one-to-one): Vanilla Neural Network
                            üSeq-to-Seq (many-to-many): Name entity recognition
                            üSeq-to-Vector (many-to-one): Sentiment classification
                            üVector-to-Seq (one-to-many): Music generation
                            üEncoder-Decoder (many-to-many): Machine translation
                            High Speed Network Lab 12
                            Machine Learning
                            Input and Output Sequences(2/7)
                            v Sequence to Sequence\s
                            Ø\s
                            High Speed Network Lab 13
                            Machine Learning
                            Input and Output Sequences(3/7)
                            v Sequence to vector\s
                            Ø Input sequence but ignore all outputs until the last one\s
                            ü (e.g. feed words of a review and output a sentiment score... +1 good or -1\s
                            bad)
                            High Speed Network Lab 14
                            Machine Learning
                            Input and Output Sequences(4/7)
                            v Vector to sequence:\s
                            Ø Input a vector once (or same vector repeatedly) and have RNN\s
                            output a sequence\s
                            ü (e.g. input an image and generate sequence of words used as a caption)
                            High Speed Network Lab 15
                            Machine Learning
                            Input and Output Sequences(5/7)
                            v Encoder-decoder\s
                            Ø Sequence to vector followed by vector to sequence\s
                            ü e.g. translate a sentence\s
                            ü Better than word-by-word with a sequence-sequence RNN since words at\s
                            the end of the sentence may affect how words at the start of the sentence\s
                            should be translated
                            High Speed Network Lab 16
                            Machine Learning
                            Input and Output Sequences(6/7)
                            High Speed Network Lab 17
                            Machine Learning
                            Recurrent Networks offer a lot of flexibility:
                            e.g. Machine Translation
                            seq of words -> seq of words
                            Input and Output Sequences(7/7)
                            High Speed Network Lab 18
                            Machine Learning
                            Training RNNs
                            v To train an RNN, the trick is to unroll it through time
                            v Then use regular Back-Propagation (BPTT)
                            v The weights and biases (same for each time step) are tuned via back\s
                            propagation at each time step (sum over all time steps)
                            v\s
                            High Speed Network Lab 19
                            Machine Learning
                            Forecasting a time series
                            vRNN: [batch size, time steps, dimensionality]
                            vNative forecasting: MSE: 0.02
                            v\s
                            vSimple Regression Model: MSE: 0.004: 51 parameters
                            vBasic RNN: MSE=0.014: one parameter
                            v\s
                            High Speed Network Lab 20
                            Machine Learning
                            Deep RNNs
                            v Deep RNN: stack multiple layers of cells=>More neurons, more layers
                            v Pass all outputs between layers (return_sequences=true)
                            vLast layer only feeds back single output\s
                            v\s
                            High Speed Network Lab 21
                            Machine Learning
                            Forecasting Several Time Steps Ahead
                            vNext 10 predictions
                            vFirst option: Use model trained, predict the next value, add that value as\s
                            input, use the model again\s
                            ØError might accumulate
                            ØMSE=0.029
                            High Speed Network Lab 22
                            Machine Learning
                            Forecasting Several Time Steps Ahead
                            vSecond option: Keeping the sequence to vector RNN structure
                            ØOutput=10 next values
                            ØMSE=0.008
                            v Third option: Using sequence to sequence RNN structure, at every time\s
                            step
                            ØThe model outputs a sequence of the next ten values (loss accounts for output at\s
                            each time step, not just the last one)
                            ØLoss will contain a term for the output of the RNN at each and every time step, not\s
                            just the output at the last time step\s
                            ØMore error gradients flowing through the model, and they won’t have to flow only\s
                            through time; they will also flow from the output of each time step. This will both\s
                            stabilize and speed up training
                            ØMSE=0.006 \s
                            High Speed Network Lab 23
                            Machine Learning
                            Handling Long Sequences
                            v To train an RNN on long sequences, run it over many time\s
                            steps, making the unrolled RNN a very deep network
                            ØFighting the unstable gradients problem
                            ØTackling the short-term memory problem
                            üLSTM (Long Short-Term Memory)
                            üGRU  (Gated Recurrent Unit)\s
                            High Speed Network Lab 24
                            Machine Learning
                            LSTM Cell
                            v LSTM (Long Short-Term Memory cell)
                            ØTraining will converge faster, and can detect long-term dependencies in the data\s
                            High Speed Network Lab 25
                            Machine Learning
                            LSTM
                            v What to store in the long-term state, what to throw away, what\s
                            to read from it
                            v Cell: Its state is split into two vectors: h(t) and c(t). h(t) as the short-term\s
                            state and c(t) as the long-term state
                            vInput vector: x(t)\s
                            vMain layer is the one that output g(t) using tanh()
                            vThree gates: logistic activation function: 0~1
                            ØForget gate f(t) : which parts of the long-term state should be erased
                            ØInput gate i(t) : which parts of g(t) should be added to the long-term state
                            ØOutput gate o(t) : which parts of the long-term state should be read and output\s
                            High Speed Network Lab 26
                            Machine Learning
                            LSTM Computation
                            High Speed Network Lab 27
                            Machine Learning
                            GRU Cell
                            v The Gated Recurrent Unit (GRU) cell: Simplified version of LSTM\s
                            ØProposed in 2014
                            High Speed Network Lab 28
                            Machine Learning
                            GRU
                            v A simplified version of LSTM, perform just as well
                            v Both state vector are merge into a single vector h(t)\s
                            vSingle gate controller z(t) controls forget gate and input gate
                            Ø1: forget gate is open, input gate is closed
                            Ø0: forget gate is closed, input gate is open
                            vNo output gate: the full state vector is output
                            vNew gate  r(t) : which part of the previous state will be shown\s
                            to the main layer g(t)\s
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> extractTextFromPdf(@RequestPart("file")
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                    description = "PDF 檔案",
                                                    required = true,
                                                    content = @Content(
                                                            mediaType = "multipart/form-data",
                                                            examples = @ExampleObject(value = """
                                                                    {
                                                                        "file": "PDF 檔案"
                                                                    }
                                                                    """)
                                                    )
                                              ) MultipartFile file);


    @Operation(
            summary = "從圖片中提取文字",
            description = "從圖片中提取文字。{language} 可以輸入 eng 或 chi 。" +
                    "必須指定 mediaType 為 multipart/form-data。 " +
                    "Request body 必須是圖片檔案，其中 key 為 'file'，value 為該圖片檔案"
    )
    @ApiResponse(
            responseCode = "200",
            description = "成功提取文字",
            content = @Content(
                    mediaType = "application/text",
                    examples = @ExampleObject(value = """
                            eazy
                            JWT TOKENS A
                            v JWT means JSON Web Token. It is a token implementation which will be in the JSON format and designed to
                            use for the web requests.
                            v" JWT is the most common and favorite token type that many systems use these days due to its special features
                            and advantages.
                            v JWT tokens can be used both in the scenarios of Authorization/Authentication along with Information
                            exchange which means you can share certain user related data in the token itself which will reduce the burden
                            of maintaining such details in the sessions on the server side.
                            A JWT token has 3 parts each separated by a period(.) Below is a sample JWT token,
                            RO DESNNIENIDIFEG . S (1 KxwRISMeKKF2QT4fwpMeJf36POk6yJV_adQsswsc
                            . -
                            8. Signature (Optional)
                            """)
            )

    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> extractTextFromImage(@PathVariable(value = "language") ExtractLanguage language,
                                                @RequestPart("file") MultipartFile file);
}
